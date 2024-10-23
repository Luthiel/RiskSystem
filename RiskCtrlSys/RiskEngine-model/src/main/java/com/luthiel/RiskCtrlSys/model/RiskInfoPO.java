package com.luthiel.RiskCtrlSys.model;

import lombok.Data;

/**
 * author: Luthiel
 * description: 风险信息POJO
 * date: 2023
 */

@Data
public class RiskInfoPO {

    /**
     * uid
     */
    private int user_id_int;
    /**
     * 规则命中原因
     */
    private String hit_reason;
}
