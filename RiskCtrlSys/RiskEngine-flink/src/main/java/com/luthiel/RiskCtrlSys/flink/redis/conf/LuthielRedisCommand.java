package com.luthiel.RiskCtrlSys.flink.redis.conf;

import lombok.Getter;

/**
 * author: Luthiel
 * description: Redis命令的枚举类
 * date: 2023
 */

@Getter
public enum LuthielRedisCommand {

    GET(LuthielRedisDataType.STRING);

    private LuthielRedisDataType luthielRedisDataType;

    LuthielRedisCommand(LuthielRedisDataType luthielRedisDataType) {
        this.luthielRedisDataType = luthielRedisDataType;
    }
}
