package thread.balking;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        DocEditThread docEditThread = new DocEditThread("/usr/local/Cellar","test.txt");
        docEditThread.start();
    }
}
