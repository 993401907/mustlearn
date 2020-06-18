package thread.activeobjects.old;

import java.util.Map;

/**
 * @author wulizi
 * 异步消息类
 */
public abstract class BaseMethodMessage {
    protected final OrderService orderService;
    protected final Map<String,Object> map;

    protected BaseMethodMessage(OrderService orderService, Map<String ,Object> map) {
        this.orderService = orderService;
        this.map = map;
    }

    public abstract void execute();
}
