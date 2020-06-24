package thread.eventbus;

import java.util.concurrent.Executor;

/**
 * @author wulizi
 */
public class EventBus implements Bus {
    private String busName;
    private Registry registry = new Registry();
    private Dispatcher dispatcher;
    public static final String DEFAULT_BUS_NAME = "default_bus";
    public static final String DEFAULT_TOPIC = "default";

    @Override
    public String getBusName() {
        return busName;
    }

    public EventBus(String busName, EventExceptionHandler handler, Executor executor) {
        this.busName = busName;
        this.dispatcher = Dispatcher.newDispather(executor, handler);
    }

    public EventBus(Dispatcher dispatcher) {
        this.busName = DEFAULT_BUS_NAME;
        this.dispatcher = dispatcher;
    }

    @Override
    public void register(Object object) {
        registry.bind(object);
    }

    @Override
    public void unregister(Object object) {
        registry.unbind(object);
    }

    @Override
    public void post(Object event) {
        this.post(event, DEFAULT_TOPIC);
    }

    @Override
    public void post(Object event, String topic) {
        dispatcher.dispatch(this, registry, event, topic);
    }

    @Override
    public void close() {
        this.dispatcher.close();
    }
}
