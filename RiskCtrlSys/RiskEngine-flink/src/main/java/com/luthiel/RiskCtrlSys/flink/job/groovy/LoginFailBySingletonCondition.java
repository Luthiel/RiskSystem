package com.luthiel.RiskCtrlSys.flink.job.groovy;

import com.googlecode.aviator.AviatorEvaluator;
import com.luthiel.RiskCtrlSys.flink.job.aviator.SumFunction;
import org.apache.flink.cep.pattern.conditions.SimpleCondition;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * author: Luthiel
 * description: TODO
 * date: 2023
 */

public class LoginFailBySingletonCondition<T> extends SimpleCondition<T> implements Serializable {

    /**
     * Aviator字段
     */
    private String field;
    /**
     * 规则表达式
     */
    private String expression;

    public LoginFailBySingletonCondition(String field, String expression) {
        this.field = field;
        this.expression = expression;

        //加载 Aviator 自定义函数
        AviatorEvaluator.addFunction(new SumFunction(this.field));
    }

    @Override
    public boolean filter(T event) {
        Map<String,Object> params=new HashMap<>();
        try {
            Method method = event.getClass().getMethod("getEvent_name");
            String name = (String) method.invoke(event);
            params.put("data", name);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Aviator 表达式计算
        return (Boolean) AviatorEvaluator.execute(expression, params);
//        return true;
    }
}
