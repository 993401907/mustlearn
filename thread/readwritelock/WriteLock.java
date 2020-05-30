package thread.readwritelock;


/**
 * @author wulizi
 * 写锁
 */
public class WriteLock implements Lock {
    private ReadWriteLock readWriteLock;

    public WriteLock(ReadWriteLock readWriteLock) {
        this.readWriteLock = readWriteLock;
    }

    @Override
    public void lock() {
        synchronized (readWriteLock.getMUTEX()) {
            try {
                readWriteLock.incrementWaitWriters();
                while
                (readWriteLock.getReaders() > 0 || readWriteLock.getWriter() > 0) {
                    readWriteLock.getMUTEX().wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                readWriteLock.decrementWaitWriters();
            }
            readWriteLock.incrementWriter();
        }
    }

    @Override
    public void unlock() {
        synchronized (readWriteLock.getMUTEX()) {
            readWriteLock.decrementWriter();
            readWriteLock.setPreferWriter(false);
            readWriteLock.getMUTEX().notifyAll();
        }
    }
}
