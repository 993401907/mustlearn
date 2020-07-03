package std;

import java.io.*;
import java.net.Socket;
import java.util.Locale;

/**
 * @author 伍立子
 */
public class Out {
    private static final String UTF8 = "UTF-8";

    private static final Locale US_LOCALE = new Locale("en", "US");

    private PrintWriter out;

    public Out(OutputStream os) {
        try {
            OutputStreamWriter osw = new OutputStreamWriter(os, UTF8);
            out = new PrintWriter(osw, true);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public Out() {
        this(System.out);
    }

    public Out(Socket socket) throws IOException {
        this(socket.getOutputStream());
    }

    public Out(String s) throws IOException {
        this(new FileOutputStream(s));
    }

    public void close() {
        out.close();
    }

    public void println() {
        out.println();
    }

    public void println(Object x) {
        out.println(x);
    }

    public void println(boolean x) {
        out.println(x);
    }

    /**
     * Print a char and then terminate the line.
     */
    public void println(char x) {
        out.println(x);
    }

    /**
     * Print an double and then terminate the line.
     */
    public void println(double x) {
        out.println(x);
    }

    /**
     * Print a float and then terminate the line.
     */
    public void println(float x) {
        out.println(x);
    }

    /**
     * Print an int and then terminate the line.
     */
    public void println(int x) {
        out.println(x);
    }

    /**
     * Print a long and then terminate the line.
     */
    public void println(long x) {
        out.println(x);
    }

    /**
     * Print a byte and then terminate the line.
     */
    public void println(byte x) {
        out.println(x);
    }


    /**
     * Flush the output stream.
     */
    public void print() {
        out.flush();
    }

    /**
     * Print an object and then flush the output stream.
     */
    public void print(Object x) {
        out.print(x);
        out.flush();
    }

    /**
     * Print an boolean and then flush the output stream.
     */
    public void print(boolean x) {
        out.print(x);
        out.flush();
    }

    /**
     * Print an char and then flush the output stream.
     */
    public void print(char x) {
        out.print(x);
        out.flush();
    }

    /**
     * Print an double and then flush the output stream.
     */
    public void print(double x) {
        out.print(x);
        out.flush();
    }

    /**
     * Print a float and then flush the output stream.
     */
    public void print(float x) {
        out.print(x);
        out.flush();
    }

    /**
     * Print an int and then flush the output stream.
     */
    public void print(int x) {
        out.print(x);
        out.flush();
    }

    /**
     * Print a long and then flush the output stream.
     */
    public void print(long x) {
        out.print(x);
        out.flush();
    }

    /**
     * Print a byte and then flush the output stream.
     */
    public void print(byte x) {
        out.print(x);
        out.flush();
    }

    public void printf(Locale locale, String format, Object... args) {
        out.printf(locale, format, args);
        out.flush();
    }



}
