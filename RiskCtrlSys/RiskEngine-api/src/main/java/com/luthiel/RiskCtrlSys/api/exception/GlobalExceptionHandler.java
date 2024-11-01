package com.luthiel.RiskCtrlSys.api.exception;

import com.luthiel.RiskCtrlSys.commons.exception.custom.RedisException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * author: Luthiel
 * description: 全局的异常捕抓 (Api)
 * date: 2023
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RedisException.class)
    public void RedisExceptionHandler(RedisException e) {

        //TODO 错误处理-后端
    }
}
