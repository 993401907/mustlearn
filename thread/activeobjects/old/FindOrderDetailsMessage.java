package thread.activeobjects.old;

import thread.future.Future;

import java.util.Map;

/**
 * @author wulizi
 * 获取订单信息消息
 */
public class FindOrderDetailsMessage extends BaseMethodMessage {
    protected FindOrderDetailsMessage(OrderService orderService, Map<String ,Object> map) {
        super(orderService, map);
    }
    @Override
    @SuppressWarnings("unchecked")
    public void execute() {
        Future<String> realFuture = orderService.findOrderDetails((Long) map.get("orderId"));
        ActiveFuture<String> activeFuture = (ActiveFuture<String>) map.get("activeFuture");
        try {
            String res = realFuture.get();
            activeFuture.finish(res);
        } catch (InterruptedException e) {
            e.printStackTrace();
            activeFuture.finish(null);
        }
    }
}
