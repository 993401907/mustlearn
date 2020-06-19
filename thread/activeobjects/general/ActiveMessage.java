package thread.activeobjects.general;

import thread.activeobjects.old.ActiveFuture;
import thread.future.Future;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author wulizi
 * 异步消息类
 */
class ActiveMessage {
    /**
     * 方法参数
     */
    private Object[] parameters;
    /**
     * 方法
     */
    private Method method;
    /**
     * 方法所属的类
     */
    private Object service;
    private ActiveFuture<Object> activeFuture;

    public void execute() {
        try {
            Object result = method.invoke(service,parameters);
            if (activeFuture != null) {
                Future<?> future = (Future<?>) result;
                Object realRes = future.get();
                activeFuture.finish(realRes);
            }
        } catch (IllegalAccessException | InvocationTargetException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用构建模式
     */
    private ActiveMessage(Builder builder) {
        this.activeFuture = builder.activeFuture;
        this.method = builder.method;
        this.service = builder.service;
        this.parameters = builder.parameters;
    }

    static class Builder {
        private Object[] parameters;
        private Method method;
        private Object service;
        private ActiveFuture<Object> activeFuture;

        public Builder paremeters(Object[] parameters) {
            this.parameters = parameters;
            return this;
        }

        public Builder activeFuture(ActiveFuture<Object> activeFuture) {
            this.activeFuture = activeFuture;
            return this;
        }

        public Builder method(Method method) {
            this.method = method;
            return this;
        }

        public Builder service(Object service) {
            this.service = service;
            return this;
        }

        public ActiveMessage builde() {
            return new ActiveMessage(this);
        }
    }
}
