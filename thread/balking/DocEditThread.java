package thread.balking;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author wulizi
 * 文件编辑线程(启动线程)
 */
public class DocEditThread extends Thread {
    private String filePath;
    private String fileName;
    private final Document document;
    private final String QUIT = "quit";
    private Scanner scanner = new Scanner(System.in);

    public DocEditThread(String filePath, String fileName) throws IOException {
        this.document = Document.createDoc(filePath, fileName);
    }

    @Override
    public void run() {
        try {
            int times = 0;
            while (true) {
                String line = scanner.next();
                if (QUIT.equals(line.toLowerCase())) {
                    document.close();
                    break;
                }
                document.edit(line);
                if (times == 5) {
                    document.save();
                    times = 0;
                }
                times++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
