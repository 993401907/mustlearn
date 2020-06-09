package thread.future;

/**
 * @author wulizi
 * 提交任务接口
 */
public interface FutureService<In,Out> {
    /**
     * 提交不带返回值的任务
     * @param runnable 任务
     * @return future
     */
    Future<?> submit(Runnable runnable);

    /**
     * 提交一个带返回值的任务
     * @param task .
     * @param input .
     * @return 带返回值的future
     */
    Future<Out> submit(Task<In,Out> task, In input);

    Future<Out> submit(Task<In,Out> task, In input, Callback<Out> callback);

    static <In,Out> FutureService<In, Out> newService() {
        return new FutureServiceImpl<>();
    }
}
