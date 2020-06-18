package thread.activeobjects.old;

/**
 * @author wulizi
 * orderService工厂
 */
public class OrderServiceFactory {
    private final static ActiveMessageQueue queue = new ActiveMessageQueue();
    private OrderServiceFactory() {

    }

    public static OrderService toActiveObject(OrderService orderService) {
        return new OrderServiceProxy(orderService,queue);
    }
}
