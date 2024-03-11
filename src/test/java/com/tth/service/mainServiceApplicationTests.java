package com.tth.service;

import com.tth.service.listener.EventPublisher;
import com.tth.service.listener.event.MessageSendEvent;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = {mainServiceApplication.class})
@RunWith(SpringRunner.class)
class mainServiceApplicationTests {

    @Autowired
    private EventPublisher eventPublisher;

    @Test
    public void publishEvent(){
        MessageSendEvent messageSendEvent = new MessageSendEvent("你好");
        eventPublisher.publishEvent(messageSendEvent);
        System.out.println("发送完成>>>>>>>>>>>>>>>>>>>");
    }

}
