package com.tth.service.async.test.mqtt;

import com.tth.service.async.test.mqtt.properties.MqttProperty;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.annotation.PostConstruct;

/**
 * @Auther: Kedaya
 * @Date: 2023/12/20 16:46:20
 * @ClassName: skysys_dy_ProtocolConversonService  16:46
 * @Description: 
 */
@Configuration
@Order(1)
public class MqttConfiguration {
    @Autowired
    private MqttPushClient mqttPushClient;
    @Autowired
    private MqttProperty mqttProperty;
    @Autowired
    private TopicOperation topicOperation;
    @PostConstruct
    private void init() {
        // 获取 TopicOperation 实例并将其存储在类变量中
        mqttPushClient.connectReturn(mqttProperty.getEndpoint() + ":" + mqttProperty.getPort(), "kedaya", "root", "root", 10, 20);
        topicOperation.subscribe(MqttPushClient.client, "email");
    }
}
