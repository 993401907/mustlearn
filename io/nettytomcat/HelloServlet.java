package io.nettytomcat;

import java.io.IOException;

public class HelloServlet extends HttpServlet {
    @Override
    public void doGet(Request request, Response response) throws IOException {
        response.write("12314125");
    }

    @Override
    public void doPost(Request request, Response response) {

    }
}
