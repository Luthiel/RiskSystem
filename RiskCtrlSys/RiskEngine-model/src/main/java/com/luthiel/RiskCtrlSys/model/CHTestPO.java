package com.luthiel.RiskCtrlSys.model;

import lombok.Data;

/**
 * author: Luthiel
 * description: clickhouse 测试表映射的PO对象
 * date: 2023
 */


@Data
@Deprecated
public class CHTestPO {

    private String name;

    public CHTestPO() {
    }

    public CHTestPO(String name) {
        this.name = name;
    }
}
