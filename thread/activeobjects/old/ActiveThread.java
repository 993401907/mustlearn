package thread.activeobjects.old;

public class ActiveThread extends Thread {
    private final ActiveMessageQueue queue;

    public ActiveThread(ActiveMessageQueue queue) {
        super("active Thread");
        this.queue = queue;
        setDaemon(true);
    }

    @Override
    public void run() {
        while (true) {
            BaseMethodMessage message = queue.take();
            message.execute();
        }
    }
}
