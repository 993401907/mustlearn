package thread.workthread;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Test {
    public static void main(String[] args) {
        ProductionChannel channel = new ProductionChannel();
        AtomicInteger integer = new AtomicInteger();
        IntStream.range(1,8).forEach(i ->{
            new Thread(()->{
                while (true) {
                    channel.offerProduction(new BookProduction(integer.getAndIncrement()));
                }
            }).start();
        });
    }
}
