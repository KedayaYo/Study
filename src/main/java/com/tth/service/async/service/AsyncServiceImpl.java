package com.tth.service.async.service;

import com.tth.service.async.test.mqtt.MqttPushClient;
import com.tth.service.async.test.mqtt.TopicOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

/**
 * @Auther: Kedaya
 * @Date: 2024/2/4 18:31:42
 * @ClassName: asyncService  18:31
 * @Description:
 */
@Slf4j
@Service
public class AsyncServiceImpl implements AsyncService {
    @Autowired
    private TopicOperation topicOperation;

    /**
     * 功能描述:
     *
     * @param email
     * @param msg
     * @author Kedaya
     **/
    @Async("asyncTheadPool1")
    @SneakyThrows
    @Override
    public void sendMail(String email, String msg) {
        Thread.sleep(new Random().nextInt(3000));
        topicOperation.publish(MqttPushClient.client,msg, "email");
        log.info("发送邮件给：{}，内容：{}", email, msg);
    }

    /**
     * 功能描述:
     *
     * @param email
     * @param msg
     * @return {@link CompletableFuture }
     * @author Kedaya
     **/
    @Async("asyncTheadPool2")
    @SneakyThrows
    @Override
    public CompletableFuture blockSendMails(String email, String msg) {
        Thread.sleep(new Random().nextInt(3000));
        log.info("发送邮件给：{}，内容：{}", email, msg);
        return CompletableFuture.completedFuture("发送邮件给：" + email + "，内容：" + msg + "，发送成功");
    }
}
