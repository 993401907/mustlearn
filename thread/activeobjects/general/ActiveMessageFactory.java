package thread.activeobjects.general;


import thread.activeobjects.old.ActiveFuture;
import thread.future.Future;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author wulizi
 * 异步消息工厂
 */
public class ActiveMessageFactory {
    private final static ActiveMessageQueue QUEUE = new ActiveMessageQueue();

    public static <T> T active(T instance) {
        Object proxy = Proxy.newProxyInstance(instance.getClass().getClassLoader(),
                instance.getClass().getInterfaces(), new ActiveInvocationHandler<>(instance)
        );
        return (T) proxy;
    }

    /**
     * 使用反射
     */
    private static class ActiveInvocationHandler<T> implements InvocationHandler {

        private final T instance;

        private ActiveInvocationHandler(T instance) {
            this.instance = instance;
        }


        @Override
        @SuppressWarnings("unchecked")
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            //获取带有method的注解，将方法信息存入activeMessage放入队列
            if (method.isAnnotationPresent(ActiveMethod.class)) {
                checkMethod(method);
                ActiveMessage.Builder builder = new ActiveMessage.Builder()
                        .method(method).paremeters(args).service(instance);
                Object result = null;
                if (this.isFutureReturnType(method)) {
                    result = new ActiveFuture<>();
                    builder.activeFuture((ActiveFuture<Object>) result);
                }
                QUEUE.offer(builder.builde());
                return result;
            } else {
                return method.invoke(instance, args);
            }
        }

        private void checkMethod(Method method) {
            if (!isFutureReturnType(method) && !isVoidReturnType(method)) {
                throw new IllegalArgumentException("方法的返回值必须为Future或者void");
            }
        }

        private boolean isVoidReturnType(Method method) {
            return method.getReturnType().equals(Void.class);
        }

        private boolean isFutureReturnType(Method method) {
            return method.getReturnType().isAssignableFrom(Future.class);
        }
    }
}
