package com.luthiel.RiskCtrlSys.commons.exception;

import com.luthiel.RiskCtrlSys.commons.exception.custom.RedisException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * author: Luthiel
 * description: 自定义异常单元测试
 * date: 2023
 */
@SpringBootTest(classes = CustomExceptionTest.class)
public class CustomExceptionTest {

    @DisplayName("测试自定义异常捕捉")
    @Test
    public void testThrowCustomException(){
        Throwable thrown =
                assertThrows(
                        RedisException.class,
                        () -> CustomExceptionDemo.throwCustomException()
                );

        System.out.println("thrown数据类型: "+thrown);
    }
}
