package thread.balking;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wulizi
 * balking设计模式 文档保存和写入
 */
public class Document {
    private FileWriter writer;
    private boolean changed;
    private List<String> content = new ArrayList<>();
    private final AutoSaveThread autoSaveThread;
    private Document(String filePath, String fileName) throws IOException {
        File file = new File(filePath,fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        this.writer = new FileWriter(file,true);
        this.autoSaveThread = new AutoSaveThread(this);
        autoSaveThread.start();
    }

    public static Document createDoc(String filePath, String fileName) throws IOException {
        return new Document(filePath, fileName);
    }

    public void edit(String line) {
        synchronized (this) {
            this.content.add(line);
            this.changed = true;
        }
    }
    public void save() throws IOException {
        synchronized (this) {
            if (!changed) {
                return;
            }
            for (String line : this.content) {
                writer.write(line);
                writer.write("\r\n");
            }
            this.writer.flush();
            this.changed = false;
            this.content.clear();
        }
    }

    public void close() throws IOException {
        autoSaveThread.interrupt();
        writer.close();
    }

}
