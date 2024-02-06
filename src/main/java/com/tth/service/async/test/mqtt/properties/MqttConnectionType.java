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
public enum MqttConnectionType {
    /**
     * 内网(默认)
     */
    INTRANET("intranet", "内网"),
    /**
     * 外网
     */
    EXTRANET("extranet", "外网");


    /**
     * 编码
     */
    private final String code;
    /**
     * 名称
     */
    private final String name;
}
