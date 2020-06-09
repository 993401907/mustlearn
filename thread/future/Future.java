package thread.future;


/**
 * @author wulizi
 * Future接口
 */
public interface Future<T> {
    /**
     * 获取返回值 阻塞方法
     * @return 返回值
     * @throws InterruptedException .
     */
    T get() throws InterruptedException;

    boolean done();
}
