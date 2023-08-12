package com.yuier.handler.exception;

import com.yuier.domain.ResponseResult;
import com.yuier.enums.AppHttpCodeEnum;
import com.yuier.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/6 15:57
 **/

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SystemException.class)  // 用于处理 SystemException 异常
    public ResponseResult systemExceptionHandler(SystemException e) {
        // 打印异常信息
        log.error("出现异常！", e);
        // 从异常对象中获取提示信息并封装返回
        return ResponseResult.errorResult(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)  // 用于处理 Exception 异常
    public ResponseResult exceptionHandler(Exception e) {
        // 打印异常信息
        log.error("出现异常！", e);
        // 从异常对象中获取提示信息并封装返回
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), e.getMessage());
    }
}
