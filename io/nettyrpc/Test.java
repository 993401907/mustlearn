package io.nettyrpc;

import io.nettyrpc.api.HelloService;
import io.nettyrpc.proxy.RpcProxy;

public class Test {
    public static void main(String[] args) {
        HelloService helloService = RpcProxy.create(HelloService.class);
        System.out.println(helloService.hello("123"));
    }
}
