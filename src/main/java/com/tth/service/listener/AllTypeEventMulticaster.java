package com.tth.service.listener;

import com.tth.service.listener.enums.EventTypeEnum;
import com.tth.service.listener.annotation.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Component("applicationEventMulticaster")
public class AllTypeEventMulticaster extends SimpleApplicationEventMulticaster {

    // public AllTypeEventMulticaster() {
    //     System.out.println("初始化AllTypeEventMulticaster>>>>>>>>>");
    //     setTaskExecutor(Executors.newFixedThreadPool(10));
    // }
    @Autowired
    public AllTypeEventMulticaster(@Qualifier("asyncTheadPool2") Executor asyncThreadPool2) {
        System.out.println("初始化AllTypeEventMulticaster>>>>>>>>>");
        setTaskExecutor(asyncThreadPool2);
    }


    @Override
    public void multicastEvent(ApplicationEvent event, ResolvableType eventType) {
        // System.out.println("AllTypeEventMulticaster>>>>>>>>>>>>>>>>>>>>>>");
        // 默认异步
        EventTypeEnum defaultEventType = EventTypeEnum.SYNC;
        ResolvableType type = (eventType != null ? eventType : resolveDefaultEventType(event));
        for (final ApplicationListener<?> listener : getApplicationListeners(event, type)) {
            Class<? extends ApplicationListener> cls = listener.getClass();
            try {
                Method onApplicationEventMethod = cls.getMethod("onApplicationEvent", ApplicationEvent.class);
                if (onApplicationEventMethod.isAnnotationPresent(EventType.class)) {
                    EventType annotation = onApplicationEventMethod.getAnnotation(EventType.class);
                    defaultEventType = annotation.value();
                }
                Executor executor = getTaskExecutor();
                if (executor != null && EventTypeEnum.ASYNC.equals(defaultEventType)) {
                    executor.execute(() -> {
                        invokeListener(listener, event);
                    });
                } else {
                    invokeListener(listener, event);
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    private ResolvableType resolveDefaultEventType(ApplicationEvent event) {
        return ResolvableType.forInstance(event);
    }
}
