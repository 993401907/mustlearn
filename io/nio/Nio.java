package io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author wulizi
 */
public class Nio {
    public static void main(String[] args) throws IOException {
        Nio nio = new Nio();
         nio.listen();
    }
    private Selector selector;
    private int port = 8090;
    private ByteBuffer buffer;
    private String content;

    public Nio() throws IOException {
        this.selector = getSelector();
        this.buffer = ByteBuffer.allocate(1024);
    }

    private Selector getSelector() throws IOException {
        Selector sel = Selector.open();
        ServerSocketChannel server = ServerSocketChannel.open();
        server.configureBlocking(false);

        ServerSocket socket = server.socket();
        socket.bind(new InetSocketAddress(port));
        server.register(sel, SelectionKey.OP_ACCEPT);
        return sel;
    }

    public void listen() {
        try {
            while (true) {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    process(key);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void process(SelectionKey key) throws IOException {
        if (key.isAcceptable()) {
            ServerSocketChannel server = (ServerSocketChannel) key.channel();
            SocketChannel channel = server.accept();
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ);
        } else if (key.isReadable()) {
            SocketChannel channel = (SocketChannel) key.channel();
            int len = channel.read(buffer);
            if (len > 0) {
                buffer.flip();
                content = new String(buffer.array(), 0, len);
                SelectionKey skey = channel.register(selector, SelectionKey.OP_WRITE);
                skey.attach(content);
            } else {
                channel.close();
            }
            buffer.clear();
        } else if (key.isWritable()) {
            SocketChannel channel = (SocketChannel) key.channel();
            String content = (String) key.attachment();
            ByteBuffer block = ByteBuffer.wrap(("输出内容:" + content).getBytes());
            channel.write(block);
        }
    }
}
