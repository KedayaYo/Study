package com.tth.service.async.test.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class MqttPushClient {

    private static final Logger log = LoggerFactory.getLogger(MqttPushClient.class);

    @Autowired
    private ApplicationContext applicationContext;
    public static MqttClient client;

    /**
     * 功能描述:
     *
     * @return void
     * @Author Kedaya
     * @Description  连接MQTT
     * @Date 2023/11/24 1:36
     * @Param [host, clientID, username, password, timeout, keepalive, topicList]
     **/
    public void connect(String host, String clientID, String username, String password, int timeout, int keepalive) {

        MqttClient client;
        try {
            client = new MqttClient(host, clientID, new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            if (!StringUtils.hasText(username)) {
                options.setUserName(username);
            }
            if (!StringUtils.hasText(password)) {
                options.setPassword(password.toCharArray());
            }
            options.setConnectionTimeout(timeout);
            options.setKeepAliveInterval(keepalive);
            options.setCleanSession(true);
            options.setAutomaticReconnect(true); // 启用自动重新连接

            try {
                // 设置回调类
                PushCallback callback = applicationContext.getBean(PushCallback.class);
                callback.setMqttClient(client);
                client.setCallback(callback);
                client.connect(options);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public MqttClient connectReturn(String host, String clientID, String username, String password, int timeout, int keepalive) {
        try {
            client = new MqttClient(host, clientID, new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            if (!StringUtils.hasText(username)) {
                options.setUserName(username);
            }
            if (!StringUtils.hasText(password)) {
                options.setPassword(password.toCharArray());
            }
            options.setConnectionTimeout(timeout);
            options.setKeepAliveInterval(keepalive);
            options.setCleanSession(true);
            options.setAutomaticReconnect(true); // 启用自动重新连接

            try {
                // 设置回调类
                PushCallback callback = applicationContext.getBean(PushCallback.class);
                callback.setMqttClient(client);
                client.setCallback(callback);
                client.connect(options);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return client;
    }

    /**
     * Signature 鉴权模式下构造方法
     *
     * @param instanceId MQ4IOT 实例 ID，购买后控制台获取
     * @param accessKey  账号 accesskey，从账号系统控制台获取
     * @param secretKey  账号 secretKey，从账号系统控制台获取
     * @param clientId   MQ4IOT clientId，由业务系统分配
     * @param timeout
     * @param keepalive
     */
    // public MqttConnectOptions connectionOptionWrapper(String instanceId, String accessKey, String secretKey,
    //                                                   String clientId, int timeout, int keepalive) throws NoSuchAlgorithmException, InvalidKeyException {
    //     MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
    //     mqttConnectOptions.setUserName("Signature|" + accessKey + "|" + instanceId);
    //     mqttConnectOptions.setPassword(Tools.macSignature(clientId, secretKey).toCharArray());
    //     mqttConnectOptions.setCleanSession(false);
    //     mqttConnectOptions.setKeepAliveInterval(keepalive);
    //     mqttConnectOptions.setAutomaticReconnect(true);
    //     mqttConnectOptions.setMqttVersion(MQTT_VERSION_3_1);
    //     mqttConnectOptions.setConnectionTimeout(timeout);
    //     return mqttConnectOptions;
    // }

    /**
     * 功能描述:
     *
     * @return void
     * @Author Kedaya
     * @Description  阿里云连接MQTT
     * @Date 2023/12/20 2:01
     * @Param [identifier, host, clientID, instanceId, accessKey, secretKey, timeout, keepalive]
     **/
    // public void aliConnect(String identifier, String host, String clientID, String instanceId, String accessKey, String secretKey, int timeout, int keepalive) {
    //     MqttClient client;
    //     try {
    //         client = new MqttClient(host, clientID, new MemoryPersistence());
    //         MqttConnectOptions options = connectionOptionWrapper(instanceId, accessKey, secretKey, clientID, timeout, keepalive);
    //         try {
    //             // 设置回调类
    //             PushCallback callback = applicationContext.getBean(PushCallback.class);
    //             callback.setMqttClient(client);
    //             client.setCallback(callback);
    //             client.connect(options);
    //             MqttClientCacheUtil.addMqttClient(identifier, client); // 将新创建的客户端保存到缓存中
    //         } catch (Exception e) {
    //             e.printStackTrace();
    //         }
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    //
    // }

}
