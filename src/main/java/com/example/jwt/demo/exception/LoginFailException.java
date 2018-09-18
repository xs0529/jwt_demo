package com.example.jwt.demo.exception;

import org.springframework.security.authentication.InternalAuthenticationServiceException;

/**
 * @author: 谢霜
 * @Date: 2018/09/14 上午 10:46
 * @Description:
 */
public class LoginFailException extends InternalAuthenticationServiceException {
    public LoginFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginFailException(String message) {
        super(message);
    }
}
