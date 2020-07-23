package io.nettyrpc.proxy;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.nettyrpc.protocol.InvokeProtocol;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author wulizi
 */
public class RpcProxy {
    public static  String hosts = "localhost";
    private static int port = 8081;


    @SuppressWarnings("unchecked")
    public static <T> T create(Class<?> clazz) {
           MethodProxy proxy = new MethodProxy(clazz);
           Class<?>[] interfaces = clazz.isInterface() ? new Class[]{clazz}:clazz.getInterfaces();
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),interfaces,proxy);
    }

    private static class MethodProxy implements InvocationHandler{

        private Class<?> clazz;

        public MethodProxy(Class<?> clazz) {
            this.clazz = clazz;
        }


        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (Object.class.equals(method.getDeclaringClass())) {
                return method.invoke(this, args);
            }else {
                return rpcInvoke(proxy,method,args);
            }
        }

        public Object rpcInvoke(Object proxy, Method method, Object[] args) {
            InvokeProtocol msg = new InvokeProtocol();
            msg.setClassName(this.clazz.getName());
            msg.setMethodName(method.getName());
            msg.setValues(args);
            msg.setParameTypes(method.getParameterTypes());

            final ProxyHandler handler =new ProxyHandler();
            EventLoopGroup group = new NioEventLoopGroup();
            try {
                Bootstrap bootstrap = new Bootstrap();
                bootstrap.group(group)
                        .channel(NioSocketChannel.class)
                        .option(ChannelOption.TCP_NODELAY, true)
                        .handler(new ChannelInitializer<SocketChannel>() {

                            @Override
                            protected void initChannel(SocketChannel socketChannel) throws Exception {
                                ChannelPipeline pipeline = socketChannel.pipeline();
                                pipeline.addLast("frameDecoder",
                                        new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                                pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
                                pipeline.addLast("encoder", new ObjectEncoder());
                                pipeline.addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                                pipeline.addLast("handler", handler);
                            }
                        });
                ChannelFuture future = bootstrap.connect(hosts,port).sync();
                future.channel().writeAndFlush(msg).sync();
                future.channel().closeFuture().sync();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                group.shutdownGracefully();
            }
            return handler.getResponse();
        }
    }
}
