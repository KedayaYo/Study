package com.tth.service.pojo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;

/**
 * @Auther: Kedaya
 * @Date: 2024/3/12 11:06:54
 * @ClassName: asyncService  11:06
 * @Description:
 */
@Data
@Builder
public class User {
    private String name;
    private String password;
    private String email;
    private String phone;
    private String address;
    private CommandParameters commandParameters;

    @Data
    @Builder
    public static class CommandParameters {
        private Long timestamp;
        private Object data;
    }
}
