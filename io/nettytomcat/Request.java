package io.nettytomcat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * @author wulizi
 */
public class Request {
    private ChannelHandlerContext ctx;
    private HttpRequest request;

    public Request(ChannelHandlerContext ctx, HttpRequest request) {
        this.ctx = ctx;
        this.request = request;
    }

    public String getMethod() {
        return request.method().name();
    }

    public String getUrl() {
        return request.uri();
    }

    public Map<String, List<String>> getParameters() {
        QueryStringDecoder decoder = new QueryStringDecoder(request.uri());
        return decoder.parameters();
    }

    public String getParameter(String name) {
        Map<String, List<String>> paramters = getParameters();
        List<String> params = paramters.get(name);
        return Optional.ofNullable(params).map(p -> p.get(0)).orElse(null);
    }
}
