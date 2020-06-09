package thread.future;

/**
 * @author wulizi
 * 回调
 */
@FunctionalInterface
public interface Callback<T> {
    /**
     * 回调方法
     * @param t .
     */
    void call(T t);
}
