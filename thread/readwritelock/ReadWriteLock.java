package thread.readwritelock;

/**
 * @author Administrator
 * 读写锁接口
 */
public interface ReadWriteLock {
    /**
     * 增加一个读线程
     */
    void incrementReader();

    /**
     * 增加一个写线程
     */
    void incrementWriter();

    /**
     * 减少一个等待写入
     */
    void decrementWaitWriters();
    /**
     * 减少一个读线程
     */
    void decrementReader();

    /**
     * 减少一个写线程
     */
    void decrementWriter();

    /**
     * 增加一个等待写入
     */
    void incrementWaitWriters();
    /**
     * @return 获取读锁
     */
    ReadLock readLock();

    /**
     * @return 获取写锁
     */
    WriteLock writeLock();
    public int getWriter() ;

    public int getReaders();

    public int getWaitingWriters();

    public boolean isPreferWriter();
    public void setPreferWriter(boolean preferWriter);
    public Object getMUTEX();
    /**
     * 工厂方法
     */
    private static ReadWriteLock getInstance(){
        return new ReadWriteLockImpl();
    }
    private static ReadWriteLock getInstance(boolean preferWriter){
        return new ReadWriteLockImpl(preferWriter);
    }
}
