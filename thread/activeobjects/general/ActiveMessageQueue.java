package thread.activeobjects.general;

import list.LinkedList;


/**
 * @author wulizi
 */
public class ActiveMessageQueue {
    private LinkedList<ActiveMessage> messages = new LinkedList<>();

    public ActiveMessageQueue() {
        new ActiveThread(this).start();
    }

    public void offer(ActiveMessage message) {
        synchronized (this) {
            messages.add(message);
            this.notify();
        }
    }

    public ActiveMessage take() {
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
