package io.nettytomcat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;

/**
 * @author wulizi
 */
public class TomcatHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            System.out.println("hello");
            HttpRequest req = (HttpRequest) msg;
            Request request = new Request(ctx,req);
            Response response = new Response(ctx,req);
            String uri = request.getUrl();
            if ("/hello".equalsIgnoreCase(uri)) {
                new HelloServlet().service(request,response);
            }else {
                response.write("404 - not found");
            }
        }
    }
}
