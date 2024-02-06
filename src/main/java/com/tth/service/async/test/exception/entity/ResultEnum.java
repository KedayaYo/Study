package com.tth.service.async.test.exception.entity;

public enum ResultEnum {
    /** 成功 */
    REQUEST_SUCCESS(200,"请求成功"),

    /** 失败  */
    REQUEST_FAILED(-1,"请求失败"),

    /** 未知错误   */
    UNKNOWN_ERROR(500,"未知错误");


    /** 状态  */
    private Integer code;
    /** 含义 */
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
