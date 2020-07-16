package io.nettytomcat;

import java.io.IOException;

/**
 * @author wulizi
 */
public abstract class HttpServlet {
    public void service(Request request, Response response) throws IOException {
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            doGet(request, response);
        } else {
            doPost(request, response);
        }
    }

    public abstract void doGet(Request request, Response response) throws IOException;

    public abstract void doPost(Request request, Response response) throws IOException;
}
