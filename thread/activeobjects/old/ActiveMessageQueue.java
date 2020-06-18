package thread.activeobjects.old;

import list.LinkedList;

/**
 * @author wulizi
 */
public class ActiveMessageQueue {
    private LinkedList<BaseMethodMessage> messages = new LinkedList<>();

    public ActiveMessageQueue() {
        new ActiveThread(this).start();
    }

    public void offer(BaseMethodMessage message) {
        synchronized (this) {
            messages.add(message);
            this.notify();
        }
    }

    public BaseMethodMessage take() {
        synchronized (this) {
            while (messages.isEmpty()) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return messages.removeFirst();
        }
    }
}
