package com.tth.service.async.test.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenException extends RuntimeException {
    String code;
    String msg;
}
