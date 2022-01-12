package com.zgm.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公共返回对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private long code;
    private String message;
    private Object obj;

    /**
     * 成功返回结果
     * @param message
     * @return
     */
    public static Result success(String message) {
        return new Result(200, message, null);
    }

    /**
     * 成功返回结果
     * @param message
     * @param obj
     * @return
     */
    public static Result success(String message, Object obj) {
        return new Result(200, message, obj);
    }

    /**
     * 失败返回结果
     * @param message
     * @return
     */
    public static Result error(String message) {
        return new Result(500, message, null);
    }

    public static Result error(String message, Object obj) {
        return new Result(500, message, obj);
    }
}
