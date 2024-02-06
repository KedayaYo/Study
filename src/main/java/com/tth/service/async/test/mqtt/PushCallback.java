package com.tth.service.async.test.mqtt;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSONObject;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author Kedaya
 * @Date
 * @Description 消息回调，处理接收的消息
 */
@Component
@Scope("prototype") // 添加这个注解来使实例具有多例性
public class PushCallback implements MqttCallbackExtended {

    private static final Logger log = LoggerFactory.getLogger(PushCallback.class);

    // 添加一个存储标识符的变量
    private MqttClient mqttClient;

    /**
     * 添加一个设置标识符的方法
     *
     * @param mqttClient
     */
    public void setMqttClient(MqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    /**
     * 连接成功后的回调 可以在这个方法执行 订阅主题  生成Bean的 MqttConfiguration方法中订阅主题 出现bug
     * 重新连接后  主题也需要再次订阅  将重新订阅主题放在连接成功后的回调 比较合理
     *
     * @param reconnect
     * @param serverURI
     */
    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        log.info("MQTT 连接成功，连接方式：{}", reconnect ? "重连" : "直连");
    }

    /**
     * 功能描述:
     *
     * @return void
     * @Author Kedaya
     * @Description 重连
     * @Date 2023/11/24 15:36
     * @Param [cause]
     **/
    @Override
    public void connectionLost(Throwable cause) {        // 连接丢失后，一般在这里面进行重连
        if (ObjectUtil.isNotEmpty(mqttClient)) {
            log.info("MQTT {} 连接断开，重连", mqttClient.getClientId());
            try {
                mqttClient.reconnect();
                log.info("MQTT {} 已重连", mqttClient.getClientId());
            } catch (MqttException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 功能描述:
     *
     * @return void
     * @Author Kedaya
     * @Description 发送消息，消息到达后处理方法
     * @Date 2023/11/24 15:36
     * @Param [token]
     **/
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // int messageId = token.getMessageId();
        // String[] topics = token.getTopics();
        // log.info("消息发送完成,messageId={},topics={}", messageId, topics.toString());
    }

    /**
     * 功能描述:
     *
     * @return void
     * @Author Kedaya
     * @Description 订阅主题接收到消息处理方法
     * @Date 2023/11/24 15:36
     * @Param [topic, message]
     **/
    @Override
    public synchronized void messageArrived(String topic, MqttMessage message) {
        String messageStr = new String(message.getPayload());
        log.info("接收的主题：" + topic + ";接收到的信息：" + JSONObject.toJSONString(messageStr));
    }

}
