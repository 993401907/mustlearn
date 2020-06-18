package thread.activeobjects.old;

import thread.future.Future;

/**
 * @author wulizi
 * 订单服务接口
 */
public interface OrderService {
    /**
     * 根据订单id获取订单信息
     * @param orderId 订单id
     * @return  future模式
     */
    Future<String> findOrderDetails(long orderId);

    /**
     * 提交订单
     * @param account 账户信息
     * @param orderId 订单id
     */
    void order(String account, long orderId);
}
