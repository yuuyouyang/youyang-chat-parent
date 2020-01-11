package com.nf.yy.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 统一响应类型
 * @author smile
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResponseVO<T> {

    /** 响应状态 */
    private Boolean state;
    /** 响应消息 */
    private String message;
    /** 响应结果 */
    private T data;

    private ResponseVO(T data) {
        this(true, "响应已完成", data);
    }

    private ResponseVO(Boolean state, String message) {
        this(state, message, null);
    }

    /** 默认响应成功返回执行结果 */
    public static <T> ResponseVO success(T data) {
        return new ResponseVO(data);
    }

    /** 默认响应失败返回失败原因 */
    public static ResponseVO failed(String message) {
        return new ResponseVO(false, message);
    }

    /** 自定义响应状态与结果 */
    public static <T> ResponseVO builder(Boolean state, String message, T data) {
        return new ResponseVO(state, message, data);
    }

}
