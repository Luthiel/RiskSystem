package com.luthiel.RiskCtrlSys.commons.exception.custom;

import com.luthiel.RiskCtrlSys.commons.exception.BizRuntimeException;
import com.luthiel.RiskCtrlSys.commons.exception.enums.BizExceptionInfo;

/**
 * author: Luthiel
 * description: 工具类自定义错误
 * date: 2023
 */

public class UtilException extends BizRuntimeException {
    /**
     * author: Luthiel
     * description: 自定义异常类构造方法
     *
     * @param info :  自定义异常枚举对象
     * @return null
     */
    public UtilException(BizExceptionInfo info) {
        super(info);
    }
}
