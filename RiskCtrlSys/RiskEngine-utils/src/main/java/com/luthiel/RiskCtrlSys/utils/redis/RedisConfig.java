package com.luthiel.RiskCtrlSys.utils.redis;

import com.alibaba.fastjson2.support.spring.data.redis.GenericFastJsonRedisSerializer;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.MapPropertySource;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * author: Luthiel
 * description: Redis 配置类
 * date: 2023
 */

@Configuration
public class RedisConfig {

    // Redis配置信息读取类
    @Autowired
    private RedisProperties redisProperties;

    // 连接池配置信息读取类
    @Autowired
    private RedisPoolProperties redisPoolProperties;

    /**
     * author: Luthiel
     * description: RedisTemplate的配置
     * @param :
     * @return org.springframework.data.redis.core.RedisTemplate
     */
    @Bean
    public RedisTemplate<String,Object> redisTemplate() {

        RedisTemplate<String,Object> redisTemplate =  new RedisTemplate<>();
        /*
         * 常用的RedisSerializer
         * 1. Jackson2JsonRedisSerializer
         * 2. GenericFastJsonRedisSerializer 或 FastJsonRedisSerializer
         * 3. StringRedisSerializer (只能支持String)
         */

        // key, value 序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericFastJsonRedisSerializer());

        //连接驱动的配置
        redisTemplate.setConnectionFactory(lettuceConnectionFactory());

        return redisTemplate;
    }


    /**
     * author: Luthiel
     * description: Lettuce连接驱动的配置
     * @param :
     * @return org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
     */
    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        // redis 集群的配置: 只有集群地址
        Map<String, Object> map = new HashMap<>();
        map.put("spring.redis.cluster.nodes", redisProperties.getNodes()); // 集群列表 ip: port

        RedisClusterConfiguration redisClusterConfiguration =
                new RedisClusterConfiguration(
                        new MapPropertySource("RedisClusterConfiguration", map)
                );

        /*
         * redis集群方式的不同，需要使用对应的Redis配置实现类
         *
         * RedisClusterConfiguration: 集群
         * RedisSentinelConfiguration：哨兵
         * RedisStandaloneConfiguration：单机
         */

        // Lettuce连接池的配置
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxWait(redisPoolProperties.getMaxWait());
        genericObjectPoolConfig.setMinIdle(redisPoolProperties.getMinIdle());
        genericObjectPoolConfig.setMaxTotal(redisPoolProperties.getMaxActive());
        genericObjectPoolConfig.setMaxWait(redisPoolProperties.getMaxWait());

        LettucePoolingClientConfiguration lettucePoolingClientConfiguration =
                LettucePoolingClientConfiguration.builder()
                        .poolConfig(genericObjectPoolConfig)
                        .build();


        return new LettuceConnectionFactory(
                redisClusterConfiguration,
                lettucePoolingClientConfiguration);
    }
}
