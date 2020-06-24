package thread.eventbus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/**
 * @author wulizi
 */
public class Dispatcher {
    private EventExceptionHandler handler;
    private Executor executor;

    public void dispatch(Bus bus, Registry registry, Object event, String topic) {
        ConcurrentLinkedQueue<Subscriber> subscribers = registry.scanSubsciber(topic);
        if (subscribers == null) {
            if (handler != null) {
                handler.handle(new IllegalArgumentException("这个topic没有绑定"),
                        new BaseEventContext(bus.getBusName(), null, event));
            }
            return;
        }
        subscribers.stream().filter(subscriber -> !subscriber.isDisabled())
                .filter(subscriber -> {
                    Method subMethod = subscriber.getSubsMethod();
                    Class<?> clazz = subMethod.getParameterTypes()[0];
                    return clazz.isAssignableFrom(event.getClass());
                }).forEach(subscriber -> realInvokeSubscribe(subscriber, event, bus));
    }

    private void realInvokeSubscribe(Subscriber subscriber, Object event, Bus bus) {
        Method subMethod = subscriber.getSubsMethod();
        Object subObj = subscriber.getSubsObj();
        executor.execute(() -> {
            try {
                subMethod.invoke(subObj, event);

            } catch (IllegalAccessException | InvocationTargetException e) {
                if (handler != null) {
                    handler.handle(e,
                            new BaseEventContext(bus.getBusName(), subscriber, event));
                }
            }
        });
    }

    public void close() {
        if (executor instanceof ExecutorService) {
            ((ExecutorService) executor).shutdown();
        }
    }

    public static Dispatcher newDispather(Executor executor, EventExceptionHandler handler) {
        return new Dispatcher(executor, handler);
    }

    public static Dispatcher newSeqDispather(EventExceptionHandler handler) {
        return new Dispatcher(ExecutorFactory.seqExecutor, handler);
    }

    public static Dispatcher newPreThreadDispathcer(EventExceptionHandler handler) {
        return new Dispatcher(ExecutorFactory.preThreadExecutor, handler);
    }

    private Dispatcher(Executor executor, EventExceptionHandler handler) {
        this.executor = executor;
        this.handler = handler;
    }


    private static class BaseEventContext implements EventContext {
        String busName;
        Subscriber subscriber;
        Object event;

        public BaseEventContext(String busName, Subscriber subscriber, Object event) {
            this.busName = busName;
            this.subscriber = subscriber;
            this.event = event;
        }

        @Override
        public String getSource() {
            return busName;
        }

        @Override
        public Object getSubscriber() {
            return subscriber == null ? null : subscriber.getSubsObj();
        }

        @Override
        public Method getMethod() {
            return subscriber == null ? null : subscriber.getSubsMethod();
        }

        @Override
        public Object getEvent() {
            return event;
        }
    }
}
