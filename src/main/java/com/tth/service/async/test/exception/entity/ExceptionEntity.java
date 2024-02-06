package com.tth.service.async.test.exception.entity;

/**
 * @Auther: Kedaya
 * @Date: 2023/12/18 13:31:54
 * @ClassName: flightIslandServer  13:31
 * @Description:
 */
public class ExceptionEntity {
    private Integer code;
    private String reason;

    public ExceptionEntity(Integer code, String reason) {
        this.code = code;
        this.reason = reason;
    }

    public ExceptionEntity() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
