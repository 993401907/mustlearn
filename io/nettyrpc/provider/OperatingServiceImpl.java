package io.nettyrpc.provider;

import io.nettyrpc.annotation.Provider;
import io.nettyrpc.api.OperationService;

/**
 * @author wulizi
 */
@Provider
public class OperatingServiceImpl implements OperationService {
    @Override
    public int add(int a, int b) {
        return a + b;
    }

    @Override
    public int sub(int a, int b) {
        return a - b;
    }

    @Override
    public int mult(int a, int b) {
        return a * b;
    }

    @Override
    public int div(int a, int b) {
        return a / b;
    }
}
