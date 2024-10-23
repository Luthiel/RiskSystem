package com.luthiel.RiskCtrlSys.commons.exception.custom;

import com.luthiel.RiskCtrlSys.commons.exception.BizRuntimeException;
import com.luthiel.RiskCtrlSys.commons.exception.enums.BizExceptionInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * author: Luthiel
 * description: Flink 配置信息自定义错误
 * date: 2023
 */

@Slf4j
public class FlinkPropertiesException extends BizRuntimeException {

    public FlinkPropertiesException(BizExceptionInfo info) {
        super(info);
    }
}
