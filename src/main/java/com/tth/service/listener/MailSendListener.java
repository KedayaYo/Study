package com.tth.service.listener;

import com.tth.service.listener.enums.EventTypeEnum;
import com.tth.service.listener.event.AsyncMessageSendEvent;
import com.tth.service.listener.event.MessageSendEvent;
import com.tth.service.listener.interfaces.EventType;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class MailSendListener implements ApplicationListener<AsyncMessageSendEvent> {

    @Override
    @EventType(value = EventTypeEnum.ASYNC)
    public void onApplicationEvent(AsyncMessageSendEvent event) {
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(dateStr + ">>>>>>>>> MailSendListener>>>>>>>>>>>> " + event);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
