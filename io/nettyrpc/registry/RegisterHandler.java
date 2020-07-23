package io.nettyrpc.registry;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.nettyrpc.annotation.Provider;
import io.nettyrpc.protocol.InvokeProtocol;
import list.ArrayList;
import list.List;
import lombok.extern.java.Log;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wulizi
 */
@Log
public class RegisterHandler extends ChannelInboundHandlerAdapter {
    public static ConcurrentHashMap<String ,Object> registerMap = new ConcurrentHashMap<>();
    private final List<String> classLists = new ArrayList<>();


    public RegisterHandler() {
        log.info("起飞");
        scannerClass("io.nettyrpc");
        doRegister();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Object result = new Object();

        InvokeProtocol request = (InvokeProtocol) msg;
        if (registerMap.containsKey(request.getClassName())) {
            Object clazz = registerMap.get(request.getClassName());
            Method method = clazz.getClass().getMethod(request.getMethodName(),
                    request.getParameTypes());
            result = method.invoke(clazz,request.getValues());
        }
        ctx.write(result);
        ctx.flush();
        ctx.close();
    }

    /**
     * 扫描类
     */
    private void scannerClass(String packageName) {
        URL url = this.getClass().getClassLoader().getResource(
                packageName.replaceAll("\\.","/"));
        if (url != null) {
            File dir = new File(url.getFile());
            for (File file : Objects.requireNonNull(dir.listFiles())) {
                if (file.isDirectory()) {
                    scannerClass(packageName+"."+file.getName());
                }else {
                    classLists.add(packageName+"."+
                            file.getName().replace(".class","").trim());
                }
            }
        }
    }

    /**
     * 扫描类完成注册
     */
    private void doRegister() {
        for (String className : classLists) {
            try {
                Class<?> clazz = Class.forName(className);
                if (clazz.isAnnotationPresent(Provider.class)) {
                    Class<?> i = clazz.getInterfaces()[0];
                    registerMap.put(i.getName(),clazz.newInstance());
                    log.info(clazz.getName()+"已被加载");
                }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
    }
}
