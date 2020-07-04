package io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.*;

/**
 * @author wulizi
 */
public class AioServer {
    public static void main(String[] args) {
        AioServer server = new AioServer(33069);
        server.listen();
    }
    private int port;

    public AioServer(int port) {
        this.port = port;
    }

    public void listen() {
        try {
            ExecutorService executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                    60L, TimeUnit.SECONDS, new SynchronousQueue<>());
            AsynchronousChannelGroup channelGroup = AsynchronousChannelGroup.
                    withCachedThreadPool(executorService, 1);
            AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open(channelGroup);
            server.bind(new InetSocketAddress(port));
            server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
                @Override
                public void completed(AsynchronousSocketChannel result, Object attachment) {
                    System.out.println("io开始");
                    ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
                    try {
                        buffer.clear();
                        result.read(buffer).get();
                        buffer.flip();
                        result.write(buffer);
                        buffer.flip();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }finally {
                        try {
                            result.close();
                            server.accept(null,this);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }

                @Override
                public void failed(Throwable exc, Object attachment) {
                    System.out.println("操作失败"+exc);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
