package io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;

/**
 * @author wulizi
 */
public class AioClient {
    private AsynchronousSocketChannel client;

    public AioClient() throws IOException {
        this.client = AsynchronousSocketChannel.open();
    }

    public void connect(String host, int port) {
        client.connect(new InetSocketAddress(host, port), null, new CompletionHandler<Void, Object>() {
            @Override
            public void completed(Void result, Object attachment) {
                try {
                    client.write(ByteBuffer.wrap("测试数据".getBytes())).get();
                    System.out.println("已经发送到服务器");
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                exc.printStackTrace();
            }
        });

        final ByteBuffer buffer = ByteBuffer.allocate(1024);
        client.read(buffer, null, new CompletionHandler<Integer, Object>() {
            @Override
            public void completed(Integer result, Object attachment) {
                System.out.println("获取成功");
                System.out.println(new String(buffer.array()));
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                exc.printStackTrace();
            }
        });
    }
}
