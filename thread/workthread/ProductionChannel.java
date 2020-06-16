package thread.workthread;

import list.LinkedList;

/**
 * @author wulizi
 * 加工传送带
 */
public class ProductionChannel {
    private int maxProduction;
    private final static int DEFAULT_MAX = 100;
    private final static int DEFAULT_WORKERS = 5;
    private final Worker[] workers;
    private final LinkedList<BaseProduction> productionQueue;


    public ProductionChannel() {
        this(DEFAULT_MAX, DEFAULT_WORKERS);
    }

    public ProductionChannel(int maxProduction, int workerCount) {
        if (maxProduction < 1) {
            throw new IllegalArgumentException();
        }
        this.maxProduction = maxProduction;
        this.productionQueue = new LinkedList<>();
        this.workers = new Worker[workerCount];
        for (int i = 0; i < workerCount; i++) {
            workers[i] = new Worker("worker" + i, this);
            workers[i].start();
        }
    }

    public void offerProduction(BaseProduction production) {
        synchronized (this) {
            while (productionQueue.size() >= maxProduction) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.productionQueue.add(production);
            this.notifyAll();
        }
    }

    public BaseProduction takeProduction() {
        synchronized (this) {
            while (productionQueue.size() <= 0) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            BaseProduction production = productionQueue.removeFirst();
            this.notifyAll();
            return production;
        }
    }
}
