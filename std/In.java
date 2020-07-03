package std;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;

/**
 * @author 伍立子
 */
public final class In {
    private Scanner scanner;
    private String charsetName = "ISO-8859-1";
    private Locale usLocale = new Locale("en", "US");


    /**
     * 通过输入的方式创建
     */
    private In() {
        scanner = new Scanner(new BufferedInputStream(System.in), charsetName);
        scanner.useLocale(usLocale);
    }

    /**
     * 通过socket的方式创建
     */
    public In(Socket socket) {
        try {
            InputStream is = socket.getInputStream();
            scanner = new Scanner(new BufferedInputStream(is), charsetName);
            scanner.useLocale(usLocale);
        } catch (IOException e) {
            System.err.println("Could not open " + socket);
        }
    }

    /**
     * 通过url方式创建
     */
    public In(URL url) {
        try {
            URLConnection site = url.openConnection();
            InputStream is = site.getInputStream();
            scanner = new Scanner(new BufferedInputStream(is), charsetName);
            scanner.useLocale(usLocale);
        } catch (IOException e) {
            System.err.println("Could not open " + url);
        }
    }

    /**
     * 通过文件的方式创建
     */
    public In(File file) {
        try {
            scanner = new Scanner(file, charsetName);
            scanner.useLocale(usLocale);
        } catch (FileNotFoundException e) {
            System.err.println("Could not open " + file);
        }
    }

    /**
     * 通过输入字符串来判断是从文件读取还是从url读取
     */
    public In(String s) {
        try {
            File file = new File(s);
            if (file.exists()) {
                scanner = new Scanner(file, charsetName);
                scanner.useLocale(usLocale);
                return;
            }

            URL url = getClass().getResource(s);
            if (url == null) {
                url = new URL(s);
            }
            URLConnection site = url.openConnection();
            InputStream is = site.getInputStream();
            scanner = new Scanner(new BufferedInputStream(is), charsetName);
            scanner.useLocale(usLocale);
        } catch (IOException ex) {
            System.err.println("Could not open " + s);
        }
    }

    /**
     * 输入流是否存在
     */
    public boolean exists() {
        return scanner != null;
    }

    /**
     * 输入流是否为空?
     */
    public boolean isEmpty() {
        return scanner.hasNext();
    }

    /**
     * 输入流是否还有下一行
     */
    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    /**
     * 读取和返回下一行
     */
    public String readLine() {
        String line;
        try {
            line = scanner.nextLine();
        } catch (Exception e) {
            line = null;
        }
        return line;
    }

    /**
     * 读取和返回下一个char
     */
    public char readChar() {
        String s = scanner.findWithinHorizon("(?s).",1);
        return s.charAt(0);
    }

    public String readAll() {
        if (scanner.hasNextLine()) {
            return null;
        }

        return scanner.useDelimiter("//A").next();
    }

    public String readString() {
        return scanner.next();
    }

    public double readDouble() {
        return scanner.nextDouble();
    }

    public long readLong() {
        return scanner.nextLong();
    }

    public float readFloat() {
        return scanner.nextFloat();
    }

    public byte readByte() {
        return scanner.nextByte();
    }

    /**
     * 通过字符串判断boolean值
     * 0为false，1为true
     */
    public boolean readBoolean() {
        String s = readString();
        final String t  = "true";
        final String f = "false";
        final String tn = "1";
        final String fn = "0";
        if (t.equalsIgnoreCase(s) || tn.equals(s)) {
            return true;
        }
        if (f.equalsIgnoreCase(s) || fn.equals(s)) {
            return false;
        }
        throw new InputMismatchException();
    }

    /**
     * 读取 ints 通过文件名
     */
    public static int[] readInts(String filename) {
        In in = new In(filename);
        String[] fields = Objects.requireNonNull(
                in.readAll()).trim().split("\\s+");
        int filedLen = fields.length;
        int[] vas = new int[filedLen];
        for (int i = 0; i < filedLen; i++) {
            vas[i] = Integer.parseInt(fields[i]);
        }
        return vas;
    }

    /**
     * 读取 Doubles 通过文件名
     */
    public static double[] readDoubles(String filename) {
        In in = new In(filename);
        String[] fields = Objects.requireNonNull(
                in.readAll()).trim().split("\\s+");
        int filedLen = fields.length;
        double[] vas = new double[filedLen];
        for (int i = 0; i < filedLen; i++) {
            vas[i] = Double.parseDouble(fields[i]);
        }
        return vas;
    }

    /**
     * 读取 Strings 通过文件名
     */
    public static String[] readStrings(String filename) {
        In in = new In(filename);
        return Objects.requireNonNull(
                in.readAll()).trim().split("\\s+");
    }

    public static int[] readInts() {
        In in = new In();
        String[] fields = Objects.requireNonNull(
                in.readAll()).trim().split("\\s+");
        int filedLen = fields.length;
        int[] vas = new int[filedLen];
        for (int i = 0; i < filedLen; i++) {
            vas[i] = Integer.parseInt(fields[i]);
        }
        return vas;
    }

    public static double[] readDoubles() {
        In in = new In();
        String[] fields = Objects.requireNonNull(
                in.readAll()).trim().split("\\s+");
        int filedLen = fields.length;
        double[] vas = new double[filedLen];
        for (int i = 0; i < filedLen; i++) {
            vas[i] = Double.parseDouble(fields[i]);
        }
        return vas;
    }

    public static String[] readStrings() {
        In in = new In();
        return Objects.requireNonNull(
                in.readAll()).trim().split("\\s+");
    }

    public void close() {
        scanner.close();
    }

}
