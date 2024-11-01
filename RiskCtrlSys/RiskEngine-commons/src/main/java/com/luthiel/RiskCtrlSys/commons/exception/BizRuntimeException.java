package com.luthiel.RiskCtrlSys.commons.exception;

import com.luthiel.RiskCtrlSys.commons.exception.enums.BizExceptionInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * author: Luthiel
 * description: 自定义异常类的根类
 * date: 2023
 */

/* **********************
 * 提要：
 * Java 将所有的错误封装为一个对象
 * 所有的错误的根对象 Throwable，Throwable有两个子类：Error 和 Exception
 * Error: 应用程序无法处理的错误
 * Exception：应用程序可以处理的错误，Exception 分为两种错误：
 * a. RuntimeException: 运行错误, 编译器是不会检查这种错误。
 * b. 非 RuntimeException：编译错误，在编译阶段就能够捕抓这种错误，不处理的话，编译是不会通过的。
 * *********************/

@Slf4j
public class BizRuntimeException extends RuntimeException {

    /**
     * author: Luthiel
     * description: 自定义异常类构造方法
     * @param info:  自定义异常枚举对象
     * @return null
     */
    public BizRuntimeException(BizExceptionInfo info) {
        log.error(info.getExceptionMsg());
    }
}
