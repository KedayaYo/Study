package com.tth.service.config.ftp;

import lombok.Data;
import org.apache.commons.net.ftp.FTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author Kedaya
 * @Date ftp配置
 * @Description
 */
@Data
@Component
@ConfigurationProperties(FtpProperties.PREFIX)
@Order(0)
public class FtpProperties implements Serializable {
    private static Logger LOGGER = LoggerFactory.getLogger(FtpProperties.class);
    private static final int BUFFER_SIZE = 1 * 1024 * 1024;
    /**
     * 指定配置文件application-***.yml中的属性名前缀
     */
    public static final String PREFIX = "ftp";
    private String host;
    private Integer port;
    private String username;
    private String password;

    private String baseurl;
    private Integer passiveMode = FTP.BINARY_FILE_TYPE;
    private String encoding="UTF-8";
    private int clientTimeout=120000;
    private int bufferSize;
    private int transferFileType= FTP.BINARY_FILE_TYPE;
    private boolean renameUploaded;
    private int retryTime;
}
