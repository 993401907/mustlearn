package thread.readwritelock;

/**
 * @author wulizi
 * 锁的接口
 */
public interface Lock {
    /**
     * 加锁
     */
    void lock();

    /**
     * 解锁
     */
    void unlock();
}
