package com.tth.service.async.test.mqtt.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Auther: Kedaya
 * @Date: 2023/11/23 14:09:22
 * @ClassName: flightIslandServer  14:09
 * @Description: 
 */
@Getter
@AllArgsConstructor
public enum MqttTopicsKeyEnums {
    UAV_EVENT("uav_event"),
    STATUS("status"),
    HIVE_OPERATE("hive_operate"),
    UAV_OPERATE("uav_operate"),
    UAV_EVENT_RES("uav_event_res");

    /**
     * 主题
     */
    private final String topicKey;
}
