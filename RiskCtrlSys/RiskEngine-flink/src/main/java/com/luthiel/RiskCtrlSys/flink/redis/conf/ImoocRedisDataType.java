package com.luthiel.RiskCtrlSys.flink.redis.conf;

import lombok.Getter;

/**
 * author: Luthiel
 * description: Redis数据类型的枚举类
 * date: 2023
 */

@Getter
public enum LuthielRedisDataType {

    STRING,
    HASH,
    LIST,
    SET,
    SORTED_SET,
    ;

    LuthielRedisDataType() {
    }
}
