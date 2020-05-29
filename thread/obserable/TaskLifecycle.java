package thread.obserable;

/**
 * @author wulizi
 * 任务生命周期接口
 */
public interface TaskLifecycle<T> {
    /**
     * 线程开始方法
     * @param t 当前线程
     */
    void onStart(Thread t);

    /**
     * 线程进行方法
     * @param t 当前线程
     */
    void onRunning(Thread t);

    /**
     * 线程结束方法
     * @param t 当前线程
     * @param result 返回值
     */
    void onFinish(Thread t, T result);

    /**
     * 线程出错方法
     * @param t 当前线程
     * @param e 异常
     */
    void onError(Thread t, Exception e);

    /**
     * @author wulizi
     * 接口的空实现
     */
     class EmptyTaskLifecycle<T> implements TaskLifecycle<T>{
        @Override
        public void onStart(Thread t) {

        }

        @Override
        public void onRunning(Thread t) {

        }

        @Override
        public void onFinish(Thread t, T result) {

        }

        @Override
        public void onError(Thread t, Exception e) {

        }
    }
}
