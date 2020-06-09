package thread.future;


/**
 * @author wulizi
 * future实现
 */
public class FutureTask<T> implements Future<T> {
    private T result;
    private final Object MUTUX = new Object();
    private boolean isDone;

    @Override
    public T get() throws InterruptedException {
        synchronized (MUTUX) {
            while (!isDone) {
                MUTUX.wait();
            }
            return result;
        }
    }

    protected void finish(T result) {
        synchronized (MUTUX) {
            if (isDone) {
                return;
            }
            this.result = result;
            this.isDone = true;
            MUTUX.notifyAll();
        }
    }
    @Override
    public boolean done() {
        return isDone;
    }
}
