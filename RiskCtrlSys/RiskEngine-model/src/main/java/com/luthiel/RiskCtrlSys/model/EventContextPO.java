package com.luthiel.RiskCtrlSys.model;

import lombok.Data;

/**
 * author: Luthiel
 * description: 行为事件上下文POJO对象
 * date: 2023
 */

@Data
public class EventContextPO {

    private DevicePO device;

    private ProfilePO profile;

    private ProductPO product;
}
