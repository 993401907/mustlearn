package thread.obserable;

/**
 * @author wulizi
 * 接口的空实现
 */
public class EmptyTaskLifecycle<T> implements TaskLifecycle<T>{
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
