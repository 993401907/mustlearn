package thread.latch;

import java.util.concurrent.TimeUnit;

public abstract class Latch {
    private int limit;

    public Latch(int limit) {
        this.limit = limit;
    }

    /**
     * 等待其他线程执行
     */
    public abstract void await() throws InterruptedException;

    /**
     * 带超时的等待方法
     * @param unit .
     * @param time 设置超时时间
     */
    public abstract void await(TimeUnit unit, long time) throws Exception;
    /**
     * 执行完毕 减一
     */
    public abstract void cutDown();

    /**
     * 获取当前未完成线程的数量
     * @return .
     */
    public abstract int getUndone();
}
