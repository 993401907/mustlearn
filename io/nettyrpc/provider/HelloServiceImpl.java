package io.nettyrpc.provider;

import io.nettyrpc.annotation.Provider;
import io.nettyrpc.api.HelloService;

/**
 * @author wulizi
 */
@Provider
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String name) {
        return "Hello "+name;
    }
}
