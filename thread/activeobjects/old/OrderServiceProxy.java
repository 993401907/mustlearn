package thread.activeobjects.old;

import thread.future.Future;

import java.util.HashMap;
import java.util.Map;

public class OrderServiceProxy implements OrderService {
    private final OrderService orderService;
    private final ActiveMessageQueue queue;

    public OrderServiceProxy(OrderService orderService, ActiveMessageQueue queue) {
        this.orderService = orderService;
        this.queue = queue;
    }

    @Override
    public Future<String> findOrderDetails(long orderId) {
        final ActiveFuture<String> activeFuture = new ActiveFuture<>();
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        map.put("activeFuture", activeFuture);
        BaseMethodMessage message = new FindOrderDetailsMessage(orderService, map);
        queue.offer(message);
        return activeFuture;
    }

    @Override
    public void order(String account, long orderId) {
        Map<String, Object> map = new HashMap<>();
        map.put("account", account);
        map.put("orderId", orderId);
        BaseMethodMessage message = new OrderMessage(orderService, map);
        queue.offer(message);
    }
}
