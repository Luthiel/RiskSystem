package com.luthiel.RiskCtrlSys.commons.exception.custom;

import com.luthiel.RiskCtrlSys.commons.exception.BizRuntimeException;
import com.luthiel.RiskCtrlSys.commons.exception.enums.BizExceptionInfo;

/**
 * author: Luthiel
 * description: Redis 自定义异常类
 * date: 2023
 */

public class RedisException extends BizRuntimeException {


    public RedisException(BizExceptionInfo info) {
        super(info);
    }
}
