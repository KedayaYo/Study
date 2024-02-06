package com.tth.service.async.test.mqtt.properties;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.io.Serializable;

/**
 * @author Kedaya
 * @Date mqtt配置及连接
 * @Description
 */
@Data
@Configuration
@ConfigurationProperties(MqttProperty.PREFIX)
@Order(0)
public class MqttProperty implements Serializable {
    private static Logger log = LoggerFactory.getLogger(MqttProperty.class);

    /**
     * 指定配置文件application-***.yml中的属性名前缀
     */
    public static final String PREFIX = "mqtt";
    private String endpoint;
    private Integer port;
}
