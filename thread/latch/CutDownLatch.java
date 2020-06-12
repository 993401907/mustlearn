package thread.latch;

import java.util.concurrent.TimeUnit;

public class CutDownLatch extends Latch {
    private int limit;

    public CutDownLatch(int limit) {
        super(limit);
    }

    @Override
    public void await() throws InterruptedException {
        synchronized (this) {
            while (limit > 0) {
                this.wait();
            }
        }
    }

    @Override
    public void await(TimeUnit unit, long time) throws Exception {
        if (time <= 0) {
            throw new IllegalArgumentException("时间设置不能小于0");
        }
        long remainNanos = unit.toNanos(time);
        long endTime = System.nanoTime() + remainNanos;
        synchronized (this) {
            while (limit > 0) {
                if (TimeUnit.NANOSECONDS.toMillis(remainNanos) <= 0) {
                    throw new Exception("超时");
                }
                this.wait(TimeUnit.NANOSECONDS.toMillis(remainNanos));
                remainNanos = endTime - System.nanoTime();
            }
        }
    }

    @Override
    public void cutDown() {
        synchronized (this) {
            if (limit <= 0) {
                throw new IllegalStateException();
            }
            limit--;
            this.notifyAll();
        }
    }

    @Override
    public int getUndone() {
        return limit;
    }
}
