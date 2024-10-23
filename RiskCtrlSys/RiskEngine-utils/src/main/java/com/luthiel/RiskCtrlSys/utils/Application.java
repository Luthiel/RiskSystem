package com.luthiel.RiskCtrlSys.utils;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * author: Luthiel
 * description: SpringBoot 启动类 (Hbase单元测试需要用到)
 * date: 2023
 */

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
