package thread.balking;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author wulizi
 */
public class AutoSaveThread extends Thread {
    private final Document document;


    public AutoSaveThread(Document document) {
        this.document = document;
    }

    @Override
    public void run() {
        while (true) {
            try {
                document.save();
                TimeUnit.SECONDS.sleep(1);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
