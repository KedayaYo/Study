package com.tth.service.listener.annotation;

import com.tth.service.listener.enums.EventTypeEnum;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventType {

    EventTypeEnum value() default EventTypeEnum.SYNC;
}
