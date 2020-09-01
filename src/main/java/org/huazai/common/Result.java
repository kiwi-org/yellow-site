package org.huazai.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author YanAnHuaZai
 * created at：2020-08-11 18:13
 */
@Getter
@Setter
@ToString
public class Result <T> implements Serializable {
    private static final long serialVersionUID = 7642941325775522443L;

    /**
     * 调用是否成功
     */
    private boolean success;

    /**
     * 调用结果集
     */
    private T result;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 错误描述
     */
    private String errorMsg;

    public Result(T result) {
        this.success = true;
        this.result = result;
    }

    public Result(String errorCode, String errorMsg) {
        this.success = false;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

}
