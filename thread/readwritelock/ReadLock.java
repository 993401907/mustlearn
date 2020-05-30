package thread.readwritelock;

/**
 * @author wulizi
 * 读锁
 */
public class ReadLock implements Lock {
    private ReadWriteLock readWriteLock;

    public ReadLock(ReadWriteLock readWriteLock) {
        this.readWriteLock = readWriteLock;
    }

    @Override
    public void lock() {
        synchronized (readWriteLock.getMUTEX()) {
            try {
                while (readWriteLock.getWriter() > 0
                        || (readWriteLock.isPreferWriter() && readWriteLock.getWaitingWriters() > 0)) {
                    readWriteLock.getMUTEX().wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            readWriteLock.incrementReader();
        }
    }

    @Override
    public void unlock() {
        synchronized (readWriteLock.getMUTEX()) {
            readWriteLock.decrementReader();
            readWriteLock.setPreferWriter(true);
            readWriteLock.getMUTEX().notifyAll();
        }
    }
}
