package com.luthiel.RiskCtrlSys.model;

import lombok.Data;

/**
 * author: Luthiel
 * description: Redis数据的映射对象
 * date: 2023
 */

@Data
public class RedisPO {

    /**
     * Redis 值
     */
    private String data;

    public RedisPO() {
    }

    public RedisPO(String data) {
        this.data = data;
    }
}
