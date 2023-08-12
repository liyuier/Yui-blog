package com.yuier.exception;

import com.yuier.enums.AppHttpCodeEnum;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/6 15:51
 **/
public class SystemException extends RuntimeException{

    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }

}
