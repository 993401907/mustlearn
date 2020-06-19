package thread.activeobjects.general;

import thread.activeobjects.old.OrderService;
import thread.activeobjects.old.OrderServiceImpl;
import thread.future.Future;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        OrderService orderService = ActiveMessageFactory.active(new OrderServiceImpl());
        Future<String > future = orderService.findOrderDetails(1231);
        System.out.println(future.get());
    }
}
