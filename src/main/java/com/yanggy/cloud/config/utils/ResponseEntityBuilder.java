package com.yanggy.cloud.config.utils;

import com.yanggy.cloud.config.enums.ErrorCode;

/**
 * @author derrick.yang
 * @Date 8/23/18 16:03
 */
public class ResponseEntityBuilder {

    public static <T>  ResponseEntityDto<T> buildNormalResponseEntity() {
        return new ResponseEntityDto<T>();
    }

    public static <T>  ResponseEntityDto<T> buildNormalResponseEntity(T data) {
        return new ResponseEntityDto<T>(data);
    }

    public static <T> ResponseEntityDto<T> buildErrorResponseEntity(ErrorCode errorCode) {
        return new ResponseEntityDto<T>(errorCode.getCode(), errorCode.getMsg());
    }
}
