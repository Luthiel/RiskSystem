package com.luthiel.RiskCtrlSys.commons.exception.enums;

/**
 * author: Luthiel
 * description: 异常枚举类接口
 * date:  2023
*/

public interface BizExceptionInfo {

    /**
     * author: Luthiel
     * description: 获取异常错误码
     * @param :
     * @return java.lang.String
     */
    String getExceptionCode();

    /**
     * author: Luthiel
     * description: 获取异常信息
     * @param :
     * @return java.lang.String
     */
    String getExceptionMsg();
}
