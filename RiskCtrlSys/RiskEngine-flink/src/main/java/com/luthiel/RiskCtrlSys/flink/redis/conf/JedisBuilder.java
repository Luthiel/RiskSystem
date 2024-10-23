package com.luthiel.RiskCtrlSys.flink.redis.conf;

import redis.clients.jedis.JedisCluster;

/**
 * author: Luthiel
 * description: 封装Jedis对象的redis方法
 * date: 2023
 */

public class JedisBuilder {

    private JedisCluster jedis = null;

    public JedisBuilder(JedisCluster jedisCluster) {
        this.jedis = jedisCluster;
    }

    public void close() {
        if (this.jedis != null) {
            this.jedis.close();
        }
    }

    /**
     * author: Luthiel
     * description: Redis的Get方法
     * @param key:  redis key
     * @return java.lang.String
     */
    public String get(String key) {
        return jedis.get(key);
    }
}
