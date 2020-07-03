package std;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author 伍立子
 */
public class BinaryIn {
    private static final int EOF = -1;

    private BufferedInputStream in;

    /**
     * 一个char的buffer
     */
    private int buffer;

    /**
     * 缓冲区中剩余的位数
     */
    private int N;

    /**
     * 无参构造，获取系统输入
     */
    public BinaryIn() {
        in = new BufferedInputStream(System.in);
        fillBuffer();
    }

    /**
     * 通过socket构造
     *
     * @param socket socket流
     */
    public BinaryIn(Socket socket) {
        try {
            InputStream is = socket.getInputStream();
            in = new BufferedInputStream(is);
            fillBuffer();
        } catch (IOException e) {
            System.err.println("Could not open " + socket);
        }
    }

    /**
     * 通过url构造
     *
     * @param url url
     */
    public BinaryIn(URL url) {
        try {
            URLConnection connection = url.openConnection();
            InputStream is = connection.getInputStream();
            in = new BufferedInputStream(is);
            fillBuffer();
        } catch (IOException e) {
            System.err.println("Could not open " + url);
        }
    }

    /**
     * 通过字符串自动选择是file方式还是url方式
     */
    public BinaryIn(String s) {

        try {
            File file = new File(s);
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                in = new BufferedInputStream(fis);
                fillBuffer();
                return;
            }

            URL url = getClass().getResource(s);
            if (s == null) {
                url = new URL(s);
            }
            URLConnection connection = url.openConnection();
            InputStream is = connection.getInputStream();
            in = new BufferedInputStream(is);
            fillBuffer();

        } catch (IOException e) {
            System.err.println("Could not open " + s);
        }

    }

    private void fillBuffer() {
        try {
            buffer = in.read();
            N = 8;
        } catch (IOException e) {
            System.err.println("EOF");
            buffer = EOF;
            N = EOF;
        }
    }


    public boolean exists() {
        return in != null;
    }

    public boolean isEmpty() {
        return buffer == EOF;
    }

    public boolean readBoolean() {
        if (isEmpty()) {
            throw new RuntimeException("Reading from empty input stream");
        }
        N--;
        boolean bit = ((buffer >> N) & 1) == 1;
        if (N == 0) {
            fillBuffer();
        }
        return bit;
    }

    public char readChar() {
        if (isEmpty()) {
            throw new RuntimeException("Reading from empty input stream");
        }
        int x = buffer;

        if (N == 8) {

            fillBuffer();
            return (char) (x & 0xff);
        }

        int oldN = N;
        x = x << (8 - N);
        fillBuffer();
        if (isEmpty()) {
            throw new RuntimeException("Reading from empty input stream");
        }
        N = oldN;
        x |= (buffer>>>N);
        return (char) (x & 0xff);
    }

    public char readChar(int r) {
        if (r < 1 || r > 16) throw new RuntimeException("Illegal value of r = " + r);

        // optimize r = 8 case
        if (r == 8) return readChar();

        char x = 0;
        for (int i = 0; i < r; i++) {
            x <<= 1;
            boolean bit = readBoolean();
            if (bit) x |= 1;
        }
        return x;
    }


    /**
     * Read the remaining bytes of data from the binary input stream and return as a string1.
     * @return the remaining bytes of data from the binary input stream as a <tt>string1</tt>
     * @throws RuntimeException if the input stream is empty or if the number of bits
     * available is not a multiple of 8 (byte-aligned)
     */
    public String readString() {
        if (isEmpty()) throw new RuntimeException("Reading from empty input stream");

        StringBuilder sb = new StringBuilder();
        while (!isEmpty()) {
            char c = readChar();
            sb.append(c);
        }
        return sb.toString();
    }


    /**
     * Read the next 16 bits from the binary input stream and return as a 16-bit short.
     * @return the next 16 bits of data from the binary standard input as a <tt>short</tt>
     * @throws RuntimeException if there are fewer than 16 bits available
     */
    public short readShort() {
        short x = 0;
        for (int i = 0; i < 2; i++) {
            char c = readChar();
            x <<= 8;
            x |= c;
        }
        return x;
    }

    /**
     * Read the next 32 bits from the binary input stream and return as a 32-bit int.
     * @return the next 32 bits of data from the binary input stream as a <tt>int</tt>
     * @throws RuntimeException if there are fewer than 32 bits available
     */
    public int readInt() {
        int x = 0;
        for (int i = 0; i < 4; i++) {
            char c = readChar();
            x <<= 8;
            x |= c;
        }
        return x;
    }

    /**
     * Read the next r bits from the binary input stream return as an r-bit int.
     * @param r number of bits to read.
     * @return the next r bits of data from the binary input stream as a <tt>int</tt>
     * @throws RuntimeException if there are fewer than r bits available on standard input
     */
    public int readInt(int r) {
        if (r < 1 || r > 32) throw new RuntimeException("Illegal value of r = " + r);

        // optimize r = 32 case
        if (r == 32) return readInt();

        int x = 0;
        for (int i = 0; i < r; i++) {
            x <<= 1;
            boolean bit = readBoolean();
            if (bit) x |= 1;
        }
        return x;
    }

    /**
     * Read the next 64 bits from the binary input stream and return as a 64-bit long.
     * @return the next 64 bits of data from the binary input stream as a <tt>long</tt>
     * @throws RuntimeException if there are fewer than 64 bits available
     */
    public long readLong() {
        long x = 0;
        for (int i = 0; i < 8; i++) {
            char c = readChar();
            x <<= 8;
            x |= c;
        }
        return x;
    }

    /**
     * Read the next 64 bits from the binary input stream and return as a 64-bit double.
     * @return the next 64 bits of data from the binary input stream as a <tt>double</tt>
     * @throws RuntimeException if there are fewer than 64 bits available
     */
    public double readDouble() {
        return Double.longBitsToDouble(readLong());
    }

    /**
     * Read the next 32 bits from standard input and return as a 32-bit float.
     * @return the next 32 bits of data from standard input as a <tt>float</tt>
     * @throws RuntimeException if there are fewer than 32 bits available on standard input
     */
    public float readFloat() {
        return Float.intBitsToFloat(readInt());
    }


    /**
     * Read the next 8 bits from the binary input stream and return as an 8-bit byte.
     * @return the next 8 bits of data from the binary input stream as a <tt>byte</tt>
     * @throws RuntimeException if there are fewer than 8 bits available
     */
    public byte readByte() {
        char c = readChar();
        byte x = (byte) (c & 0xff);
        return x;
    }
}
