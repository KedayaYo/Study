package com.tth.service.async;

import com.tth.service.async.service.AsyncService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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

    @GetMapping("/sendMails")
    @SneakyThrows
    public String sendMails() {
        String[] emails = new String[]{"a1@163.com","a2@163.com","a3@163.com","a4@163.com","a5@163.com"};
        for (int i = 0; i < emails.length; i++) {
            asyncService.sendMail(emails[i],"主题：这是一封异步测试邮件，直接返回");
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
                out.append(futures[i].get()+"\n");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return out.toString();
    }
}
