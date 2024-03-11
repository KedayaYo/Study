package com.tth.service.listener.event;

import org.springframework.context.ApplicationEvent;

public class MessageSendEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public MessageSendEvent(Object source) {
        super(source);
    }
}
