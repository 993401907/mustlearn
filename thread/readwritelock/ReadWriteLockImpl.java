package thread.readwritelock;


public class ReadWriteLockImpl implements ReadWriteLock {
    public int writer = 0;
    public int readers = 0;
    public int waitingWriters = 0;
    public boolean preferWriter = false;
    public final Object MUTEX = new Object();


    public void setPeferWriter(boolean preferWriter) {
        this.preferWriter = preferWriter;
    }

    public ReadWriteLockImpl(){}
    public ReadWriteLockImpl(boolean preferWriter){
        this.preferWriter = preferWriter;
    }
    @Override
    public void incrementReader() {
        this.readers++;
    }

    @Override
    public void incrementWriter() {
        this.writer++;
    }

    @Override
    public void decrementWaitWriters() {
        this.waitingWriters--;
    }

    @Override
    public void decrementReader() {
        this.readers--;
    }

    @Override
    public void decrementWriter() {
        this.writer--;
    }

    @Override
    public void incrementWaitWriters() {
        this.waitingWriters++;
    }

    @Override
    public ReadLock readLock() {
        return new ReadLock(this);
    }

    @Override
    public WriteLock writeLock() {
        return new WriteLock(this);
    }

    @Override
    public int getWriter() {
        return writer;
    }

    @Override
    public int getReaders() {
        return readers;
    }

    @Override
    public int getWaitingWriters() {
        return waitingWriters;
    }

    @Override
    public boolean isPreferWriter() {
        return preferWriter;
    }

    @Override
    public void setPreferWriter(boolean preferWriter) {
        this.preferWriter = preferWriter;
    }

    @Override
    public Object getMUTEX() {
        return MUTEX;
    }
}
