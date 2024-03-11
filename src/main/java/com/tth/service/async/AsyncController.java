package com.tth.service.async;

import com.tth.service.async.service.AsyncService;
import com.tth.service.listener.EventPublisher;
import com.tth.service.listener.event.AsyncMessageSendEvent;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

/**
 * @Auther: Kedaya
 * @Date: 2024/2/4 18:24:04
 * @ClassName: asyncService  18:24
 * @Description:
 */
@RestController
@Slf4j
public class AsyncController {
    @Autowired
    private AsyncService asyncService;
    @Autowired
    private EventPublisher eventPublisher;

    @GetMapping("/sendMails")
    @SneakyThrows
    public String sendMails() {
        String[] emails = new String[]{"a1@163.com", "a2@163.com", "a3@163.com", "a4@163.com", "a5@163.com"};
        for (int i = 0; i < emails.length; i++) {
            asyncService.sendMail(emails[i], "主题：这是一封异步测试邮件，直接返回");
        }
        return "SUCCESS";
    }

    @GetMapping("/blockSendMails")
    @SneakyThrows
    public String blockSendMails() {
        CompletableFuture[] futures = new CompletableFuture[5];

        String[] emails = new String[]{"a1@163.com", "a2@163.com", "a3@163.com", "a4@163.com", "a5@163.com"};
        for (int i = 0; i < emails.length; i++) {
            futures[i] = asyncService.blockSendMails(emails[i], "主题：这是一封异步测试邮件，等待结果返回");
        }

        // 等待所有异步任务执行完毕
        CompletableFuture.allOf(futures);

        StringBuffer out = new StringBuffer();
        for (int i = 0; i < futures.length; i++) {
            try {
                out.append(futures[i].get() + "\n");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return out.toString();
    }

    @GetMapping("/listenerBlockSendMails")
    @SneakyThrows
    public String listenerBlockSendMails() {
        AsyncMessageSendEvent asyncMessageSendEvent = new AsyncMessageSendEvent("asyncMessageSendEvent测试");
        eventPublisher.publishEvent(asyncMessageSendEvent);
        return "SUCCESS";
    }

    @GetMapping("/downloadFtpPhoto")
    @SneakyThrows
    public String downloadFtpPhoto() {
        String[] filePaths = new String[]{
                "/SITE1700017813710/A071704419032284726261/DJI_20240105094433_0005_W.JPG",
                "/SITE1677049834894/photo/A071705046307055617130/DJI_20240112160106_0001_T_航点1.JPG",
                "/SITE1677049834894/photo/A071705046307055617130/DJI_20240112160107_0001_V_航点1.JPG"};
        for (int i = 0; i < filePaths.length; i++) {
            asyncService.downloadFtpPhoto(filePaths[i].substring(filePaths[i].lastIndexOf("/") + 1), filePaths[i]);
        }
        return "SUCCESS";
    }


    @GetMapping("/blockDownloadFtpPhoto")
    @SneakyThrows
    public String blockDownloadFtpPhoto() {
        CompletableFuture[] futures = new CompletableFuture[2];

        String[] filePaths = new String[]{
                "/SITE1677049834894/photo/A071705046307055617130/DJI_20240112160106_0001_T_航点1.JPG",
                "/SITE1677049834894/photo/A071705046307055617130/DJI_20240112160107_0001_V_航点1.JPG"};
        for (int i = 0; i < filePaths.length; i++) {
            futures[i] = asyncService.blockDownloadFtpPhoto(filePaths[i].substring(filePaths[i].lastIndexOf("/") + 1), filePaths[i]);
        }

        // 等待所有异步任务执行完毕
        CompletableFuture.allOf(futures);

        StringBuffer out = new StringBuffer();
        for (int i = 0; i < futures.length; i++) {
            try {
                out.append(futures[i].get() + "\n");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return out.toString();
    }
}
