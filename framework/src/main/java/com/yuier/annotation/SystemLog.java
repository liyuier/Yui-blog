package com.yuier.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/9 17:01
 **/

@Target(ElementType.METHOD)  // 该注解可加在 method 上
@Retention(RetentionPolicy.RUNTIME)  // 注解生效于 RUNTIME 阶段
public @interface SystemLog {
    String businessName();
}
