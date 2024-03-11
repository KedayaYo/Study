package com.tth.service.listener;

import com.tth.service.listener.event.MessageSendEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class WechatSendListener implements ApplicationListener<MessageSendEvent> {

    @Override
    public void onApplicationEvent(MessageSendEvent event) {
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(dateStr + ">>>>>>>> WechatSendListener >>>>>>>>>>> " + event);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
