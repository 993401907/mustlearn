package thread.future;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author wulizi
 * FutureServer实现
 */
public class FutureServiceImpl<In, Out> implements FutureService<In, Out> {
    /**
     * 构造线程池
     */
    ThreadPoolExecutor pool =
            new ThreadPoolExecutor(2, 3, 60L,
                    TimeUnit.SECONDS, new LinkedBlockingQueue<>(10), new NamedThreadFactory("Future"));

    @Override
    public Future<?> submit(Runnable runnable) {
        final FutureTask<Void> future = new FutureTask<>();
        pool.submit(() -> {
            runnable.run();
            future.finish(null);
        });
        return future;
    }

    @Override
    public Future<Out> submit(Task<In, Out> task, In input) {
        final FutureTask<Out> future = new FutureTask<>();
        pool.submit(() -> {
            Out result = task.get(input);
            future.finish(result);
        });
        return future;
    }

    @Override
    public Future<Out> submit(Task<In, Out> task, In input, Callback<Out> callback) {
        final FutureTask<Out> future = new FutureTask<>();
        pool.submit(() -> {
            Out result = task.get(input);
            future.finish(result);
            if (callback != null) {
                callback.call(result);
            }
        });
        return future;
    }
}
