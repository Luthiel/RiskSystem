package com.luthiel.RiskCtrlSys.utils.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * author: Luthiel
 * description: Redis配置信息读取类
 * date: 2023
 */

@ConfigurationProperties(prefix = "spring.redis.cluster")
@Component
@Data
public class RedisProperties {

    /* **********************
     * tips:
     * 1. 属性的名称要和 application.yml的键的名称相同
     * 2. 属性的数据类型要和 application.yml的对应的值得数据类型相同
     * *********************/

    //集群节点列表
    private String nodes;

}
