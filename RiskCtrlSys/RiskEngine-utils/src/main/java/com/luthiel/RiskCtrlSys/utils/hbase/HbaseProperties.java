package com.luthiel.RiskCtrlSys.utils.hbase;

import java.util.Map;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * author: Luthiel
 * description: Hbase配置信息读取类
 * date: 2023
 */

@Data
@ConfigurationProperties(prefix = "hbase.conf")
public class HbaseProperties {

    private Map<String, String> confMaps;
}
