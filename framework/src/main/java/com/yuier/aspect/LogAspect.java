package com.yuier.aspect;

import com.alibaba.fastjson.JSON;
import com.yuier.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/9 17:04
 **/

@Component
@Aspect
@Slf4j
public class LogAspect {

    // 确定切点
    @Pointcut("@annotation(com.yuier.annotation.SystemLog)")
    public void point() {

    }

    // 定义通知方法
    @Around("point()")  // 定义切点为 point() 方法上所指定的切点
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {  // joinPoint 是被增强的方法的相关信息的封装对象
        Object ret;
        try {
            handleBefore(joinPoint);
            ret = joinPoint.proceed();  // 相当于调用目标方法并获取其返回值
            handleAfter(ret);
        } finally {
            // 结束后换行
            log.info("=======End=======" + System.lineSeparator());
        }
        return ret;  // 将目标方法的返回值再返回
    }

    private void handleBefore(ProceedingJoinPoint joinPoint) {

        // 获取调用当前方法的请求对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        // 获取被增强的方法上的注解对象
        SystemLog systemLog = getSystemLog(joinPoint);

        log.info("=======Start=======");
        // 打印请求 URL
        log.info("URL            : {}", request.getRequestURL());
        // 打印描述信息
        log.info("BusinessName   : {}", systemLog.businessName());
        // 打印 Http method
        log.info("HTTP Method    : {}", request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info(
                "Class Method   : {}.{}",
                joinPoint.getSignature().getDeclaringTypeName(), // 被调用方法的类名
                ((MethodSignature) joinPoint.getSignature()).getName()
        );
        // 打印请求的 IP
        log.info("IP             : {}", request.getRemoteHost());
        // 打印请求入参
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i ++) {
            if (args[i] instanceof MultipartFile) {
                String fileName = ((MultipartFile) args[i]).getOriginalFilename();
                args[i] = "MultipartFile: '" + fileName + "'";
            }
        }
        log.info(
                "Request Args   : {}",
                JSON.toJSONString(args)
        );
    }

    private void handleAfter(Object ret) {
        // 打印出参
        log.info(
                "Response       : {}",
                JSON.toJSONString(ret)
        );
    }

    private SystemLog getSystemLog(ProceedingJoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();  // 获取被增强的方法的签名，返回值为一个接口类型
        MethodSignature methodSignature =  (MethodSignature) signature;  // 将其强转为其子接口
        Method method = methodSignature.getMethod();  // 获取被增强的方法的封装对象
        SystemLog systemLog = method.getAnnotation(SystemLog.class);  // 获取 SystemLog 的注解对象
        return systemLog;
    }
}
