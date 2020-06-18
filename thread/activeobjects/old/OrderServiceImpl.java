package thread.activeobjects.old;

import thread.future.Future;
import thread.future.FutureService;

import java.util.concurrent.TimeUnit;

/**
 * @author wulizi
 */
public class OrderServiceImpl implements OrderService{
    @Override
    public Future<String> findOrderDetails(long orderId) {
        return FutureService.<Long, String>newService().submit(input ->{
            try {
                TimeUnit.SECONDS.sleep(4);
                System.out.println("订单id正在执行:"+ orderId);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "订单信息";
        },orderId,null);
    }

    @Override
    public void order(String account, long orderId) {
        try {
            TimeUnit.SECONDS.sleep(5);
            System.out.println("用户:"+account+" 订单已经提交");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
