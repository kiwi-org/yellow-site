package org.kiwi.configuration;

import org.kiwi.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常管理
 * @author YanAnHuaZai
 * created at：2020-08-11 18:10
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 管理
     * @author YanAnHuaZai
     * create 2020年08月11日18:19:46
     * @param throwable 异常信息
     * @return 返回结果
     */
    @ExceptionHandler(Throwable.class) //表示让Spring捕获到所有抛出的SignException异常，并交由这个被注解的方法处理。
    public Result<Void> handleException(Throwable throwable) {
        throwable.printStackTrace();
        return new Result<>("ERROR", throwable.getMessage());
    }
}
