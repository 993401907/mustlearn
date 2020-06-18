package thread.activeobjects.old;

import thread.future.FutureTask;

/**
 * @author wulizi
 * 异步任务
 */
public class ActiveFuture<T> extends FutureTask<T> {
    @Override
    protected void finish(T result) {
        super.finish(result);
    }
}
