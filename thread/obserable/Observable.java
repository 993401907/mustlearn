package thread.obserable;

/**
 * @author wulizi
 * 线程观察者接口
 */
public interface Observable {
    /**
     * 线程生命周期
     */
    enum Cycle {
        //开始
        STATRING,
        //运行
        RUNNING,
        //结束
        DONE,
        //错误
        ERROR;
    }

    /**
     * 获取当前线程生命周期
     * @return Cycle
     */
     Cycle getCycle();

    /**
     * 线程启动，屏蔽线程其他方法
     */
    void start();
    /**
     * 线程中断，屏蔽线程其他方法
     */
    void interrupt();
}
