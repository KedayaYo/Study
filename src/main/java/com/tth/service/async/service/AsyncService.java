package com.tth.service.async.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * @Auther: Kedaya
 * @Date: 2024/2/4 18:24:37
 * @ClassName: asyncService  18:24
 * @Description:
 */

public interface AsyncService {
    void sendMail(String email, String msg);

    CompletableFuture blockSendMails(String email, String msg);

    void downloadFtpPhoto(String fileName, String filePath);

    CompletableFuture blockDownloadFtpPhoto(String fileName, String filePath);
}
