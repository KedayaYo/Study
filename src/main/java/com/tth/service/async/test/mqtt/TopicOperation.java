package com.tth.service.async.test.mqtt;

import com.tth.service.async.test.exception.BusinessException;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Kedaya
 * @Date
 * @Description
 */
@Component
public class TopicOperation {

    private static final Logger log = LoggerFactory.getLogger(TopicOperation.class);
    private ReentrantLock PUBLISH_LOCK = new ReentrantLock();

    // 注入MqttMessageListener
    //@Autowired
    // private MqttMessageListener mqttMessageListener;

    /**
     * 订阅主题
     *
     * @param topic 主题名称
     */
    public void subscribe(MqttClient client, String topic) {
        try {
            if (client == null) {
                return;
            }
            client.subscribe(topic, 0);
            // 在订阅方法中作为参数使用
            // client.subscribe(topic, mqttMessageListener);
            log.info("订阅主题:{}", topic);
        } catch (MqttException e) {
            log.error("{}-订阅主题失败:{}", topic, e.getMessage());
            throw new BusinessException("订阅主题失败: " + e.getMessage());
        }
    }

    /**
     * 订阅主题
     *
     * @param topic
     * @param qos
     */
    public void subscribe(MqttClient client, String topic, int qos) {
        try {
            if (client == null) {
                return;
            }
            client.subscribe(topic, qos);
            // 在订阅方法中作为参数使用
            // client.subscribe(topic, mqttMessageListener);
            log.info("订阅主题:{}", topic);
        } catch (MqttException e) {
            log.error("{}-订阅主题失败:{}", topic, e.getMessage());
            throw new BusinessException("订阅主题失败: " + e.getMessage());
        }
    }

    /**
     * 发布主题
     *
     * @param client
     * @param pushMessage
     * @param topic
     */
    public void publish(MqttClient client, String pushMessage, String topic) {

        // log.info("发送MQTT消息 -- topic : {}, message : {}", topic, JsonUtils.toJsonString(pushMessage));

        MqttMessage message = new MqttMessage();
        message.setQos(0);
        // 非持久化
        message.setRetained(false);
        message.setPayload(pushMessage.getBytes());
        if (client == null) {
            return;
        }
        org.eclipse.paho.client.mqttv3.MqttTopic mTopic = client.getTopic(topic);
        if (null == mTopic) {
            log.error("主题不存在:{}", mTopic);
        }
        MqttDeliveryToken token;// Delivery:配送
        PUBLISH_LOCK.lock();// 注意：这里一定要同步，否则，在多线程publish的情况下，线程会发生死锁
        try {
            // client.publish(topic, message.getPayload(), 0, false);
            token = mTopic.publish(message);// 也是发送到执行队列中，等待执行线程执行，将消息发送到消息中间件
            // token.waitForCompletion(10000L);
        } catch (MqttPersistenceException e) {
            log.error("{}-发送主题 失败: {}", topic, e.getMessage());
            e.printStackTrace();
        } catch (MqttException e) {
            log.error("{}-发送主题 失败: {}", topic, e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            log.error("{}-发送主题 失败: {}", topic, e.getMessage());
            e.printStackTrace();
        } finally {
            PUBLISH_LOCK.unlock();
        }
    }

    /**
     * 发布主题
     *
     * @param pushMessage
     * @param topic
     * @param qos
     * @param retained
     */
    public void publish(MqttClient client, String pushMessage, String topic, int qos, boolean retained) {

        // log.info("发送MQTT消息 -- topic : {}, message : {}", topic, JsonUtils.toJsonString(pushMessage));

        MqttMessage message = new MqttMessage();
        message.setQos(qos);
        // 非持久化
        message.setRetained(retained);
        message.setPayload(pushMessage.getBytes());
        if (client == null) {
            return;
        }
        MqttTopic mTopic = client.getTopic(topic);
        if (null == mTopic) {
            log.error("主题不存在:{}", mTopic);
        }
        MqttDeliveryToken token;// Delivery:配送
        PUBLISH_LOCK.lock();// 注意：这里一定要同步，否则，在多线程publish的情况下，线程会发生死锁，分析见文章最后补充
        try {
            client.publish(topic, message.getPayload(), qos, retained);
            // token = mTopic.publish(message);//也是发送到执行队列中，等待执行线程执行，将消息发送到消息中间件
            // token.waitForCompletion(2000L);
        } catch (MqttPersistenceException e) {
            log.error("{}-发送主题 失败: {}", topic, e.getMessage());
            e.printStackTrace();
        } catch (MqttException e) {
            log.error("{}-发送主题 失败: {}", topic, e.getMessage());
            e.printStackTrace();
        } finally {
            PUBLISH_LOCK.unlock();
        }
    }

    /**
     * 发布主题
     *
     * @param pushMessage
     * @param topic
     * @param qos
     */
    public void publish(MqttClient client, String pushMessage, String topic, int qos) {
        // log.info("发送MQTT消息 -- topic : {}, message : {}", topic, JsonUtils.toJsonString(pushMessage));

        MqttMessage message = new MqttMessage();
        message.setQos(qos);
        // 非持久化
        message.setRetained(false);
        message.setPayload(pushMessage.getBytes());
        if (client == null) {
            return;
        }
        MqttTopic mTopic = client.getTopic(topic);
        if (null == mTopic) {
            log.error("主题不存在:{}", mTopic);
        }
        MqttDeliveryToken token;// Delivery:配送
        PUBLISH_LOCK.lock();// 注意：这里一定要同步，否则，在多线程publish的情况下，线程会发生死锁，分析见文章最后补充
        try {
            client.publish(topic, message.getPayload(), qos, false);
            // token = mTopic.publish(message);//也是发送到执行队列中，等待执行线程执行，将消息发送到消息中间件
            // token.waitForCompletion(2000L);
        } catch (MqttPersistenceException e) {
            log.error("{}-发送主题 失败: {}", topic, e.getMessage());
            e.printStackTrace();
        } catch (MqttException e) {
            log.error("{}-发送主题 失败: {}", topic, e.getMessage());
            e.printStackTrace();
        } finally {
            PUBLISH_LOCK.unlock();
        }
    }
}