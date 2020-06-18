package thread.activeobjects.old;

import java.util.Map;

public class OrderMessage extends BaseMethodMessage {
    protected OrderMessage(OrderService orderService, Map<String ,Object> map) {
        super(orderService, map);
    }

    @Override
    public void execute() {
        String account = (String) map.get("account");
        long orderId = (long) map.get("orderId");
        orderService.order(account,orderId);
    }
}
