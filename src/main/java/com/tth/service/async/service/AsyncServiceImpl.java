package com.tth.service.async.service;

import com.tth.service.async.test.mqtt.MqttPushClient;
import com.tth.service.async.test.mqtt.TopicOperation;
import com.tth.service.util.FTPUtil;
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
    @Autowired
    private FTPUtil ftpUtil;

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
        topicOperation.publish(MqttPushClient.client, msg, "email");
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
        topicOperation.publish(MqttPushClient.client, msg, "email");
        return CompletableFuture.completedFuture("发送邮件给：" + email + "，内容：" + msg + "，发送成功");
    }


    /**
     * 功能描述:
     *
     * @author Kedaya
     **/
    @Async("asyncTheadPool1")
    @SneakyThrows
    @Override
    public void downloadFtpPhoto(String fileName, String filePath) {
        // 获取文件的字节
        byte[] bytes = ftpUtil.downloadFile(filePath);
        topicOperation.publish(MqttPushClient.client, fileName, "ftp");
        log.info("{},下载完成", fileName);
    }


    /**
     * 功能描述:
     *
     * @return {@link CompletableFuture }
     * @author Kedaya
     **/
    @Async("asyncTheadPool2")
    @SneakyThrows
    @Override
    public CompletableFuture blockDownloadFtpPhoto(String fileName, String filePath) {
        // 获取文件的字节
        byte[] bytes = ftpUtil.downloadFile(filePath);
        topicOperation.publish(MqttPushClient.client, fileName, "ftp");
        log.info("{},下载完成", fileName);
        return CompletableFuture.completedFuture(fileName + ",下载完成!");
    }

}
