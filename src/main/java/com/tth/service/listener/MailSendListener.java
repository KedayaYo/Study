package com.tth.service.listener;

import com.tth.service.listener.enums.EventTypeEnum;
import com.tth.service.listener.event.AsyncMessageSendEvent;
import com.tth.service.listener.annotation.EventType;
import com.tth.service.pojo.Result;
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
        Result result = (Result) event.getSource();
        System.out.println(dateStr + ">>>>>>>>> MailSendListener>>>>>>>>>>>> " + result);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
