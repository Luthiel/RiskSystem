package com.luthiel.RiskCtrlSys.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * author: Luthiel
 * description: 商品信息POJO对象
 * date: 2023
 */
@Data
public class ProductPO {

    /**
     * 商品价格
     */
    private BigDecimal price;
}
