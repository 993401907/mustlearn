package io.nettyrpc.annotation;

import lombok.Data;

import java.lang.annotation.*;

/**
 * @author wulizi
 * 表示资源提供者
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Provider {
}
