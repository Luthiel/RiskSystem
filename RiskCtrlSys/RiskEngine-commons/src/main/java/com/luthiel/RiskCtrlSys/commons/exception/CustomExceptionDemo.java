package com.luthiel.RiskCtrlSys.commons.exception;

import com.luthiel.RiskCtrlSys.commons.exception.custom.RedisException;
import com.luthiel.RiskCtrlSys.commons.exception.enums.RedisExceptionInfo;

/**
 * author: Luthiel
 * description: 自定义异常类Demo
 * date: 2023
 */

public class CustomExceptionDemo {

    /**
     * author: Luthiel
     * description: 抛出自定义异常
     * @param :
     * @return void
     */
    public static void throwCustomException() throws RedisException {
        throw new RedisException(RedisExceptionInfo.REDISTEMPLATE_NULL);
    }
}
