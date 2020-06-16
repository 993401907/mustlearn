package thread.workthread;

/**
 * @author wulizi
 * 工人
 */
public class Worker extends Thread {

    private final ProductionChannel channel;

    public Worker(String workerName, ProductionChannel channel) {
        super(workerName);
        this.channel = channel;
    }

    @Override
    public void run() {
        while (true) {

            BaseProduction production = channel.takeProduction();
            System.out.println(getName() + "开始了加工了");
            production.begin();

        }
    }
}
