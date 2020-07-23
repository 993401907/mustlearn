package io.nettyrpc.protocol;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wulizi
 */
@Data
public class InvokeProtocol implements Serializable {
    private static final long serialVersionUID = 109558080020766645L;
    private String className;
    private String methodName;
    private Class<?>[] parameTypes;
    private Object[] values;
}
