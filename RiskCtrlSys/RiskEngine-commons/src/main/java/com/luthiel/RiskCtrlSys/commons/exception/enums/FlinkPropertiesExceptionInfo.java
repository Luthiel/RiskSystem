package com.luthiel.RiskCtrlSys.commons.exception.enums;

import lombok.Getter;

/**
 * author: Luthiel
 * description: Flink 配置信息异常枚举类
 * date: 2023
 */

@Getter
public enum FlinkPropertiesExceptionInfo implements BizExceptionInfo {
    // 枚举常量定义必须放在最前面
    PROPERTIES_NULL("-300", "配置参数不存在");

    private String exceptionCode;
    private String exceptionMsg;

    FlinkPropertiesExceptionInfo(
                String exceptionCode,
                String exceptionMsg) {
        this.exceptionCode = exceptionCode;
        this.exceptionMsg = exceptionMsg;
    }
}
