package com.tth.service.async.test.exception;


import com.tth.service.async.test.exception.entity.ExResultEntity;
import com.tth.service.async.test.exception.entity.ResultEnum;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private ExResultEntity exResultEntity = new ExResultEntity(ResultEnum.REQUEST_FAILED.getCode(), ResultEnum.REQUEST_FAILED.getMessage());
    private Throwable e;

    public BusinessException(ExResultEntity exResultEntity) {
        this.exResultEntity = exResultEntity;
    }

    public BusinessException(ExResultEntity exResultEntity, Throwable e) {
        this.exResultEntity = exResultEntity;
        this.e = e;
    }

    public BusinessException(String desc) {
        this.exResultEntity = new ExResultEntity(ResultEnum.REQUEST_FAILED.getCode(), desc);
    }

    public BusinessException(String desc, Throwable e) {
        this.exResultEntity = new ExResultEntity(ResultEnum.REQUEST_FAILED.getCode(), desc);
        this.e = e;
    }

    public Throwable getThrowable() {
        return e;
    }

    @Override
    public String getMessage() {
        return exResultEntity.getMsg();
    }

    public ExResultEntity getResultCode() {
        return exResultEntity;
    }

    public void setResultCode(ExResultEntity exResultEntity) {
        this.exResultEntity = exResultEntity;
    }


}
