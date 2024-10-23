package com.luthiel.RiskCtrlSys.model;

import lombok.Data;

/**
 * author: Luthiel
 * description: 行为事件上下文POJO对象
 * date: 2023
 */

@Data
public class EventContextPO {

    /**
     * 设备POJO
     */
    private DevicePO device;
    /**
     * 用户信息POJO
     */
    private ProfilePO profile;
    /**
     * 商品信息POJO
     */
    private ProductPO product;
}
