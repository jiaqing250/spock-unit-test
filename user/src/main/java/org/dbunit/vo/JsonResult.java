package org.dbunit.vo;


import lombok.Data;

@Data
public class JsonResult<T> {
    private static final int SUCCESS_CODE = 0;
    public static final int SERVER_ERROR_CODE = 500;

    private String requestId = "";
    private int code;
    private String message;
    private T data;

    protected JsonResult() {

    }

    public JsonResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> JsonResult<T> success() {
        return new JsonResult<>(SUCCESS_CODE, null, null);
    }

    public static <T> JsonResult<T> success(T data) {
        return new JsonResult<>(SUCCESS_CODE, null, data);
    }

    public static <T> JsonResult<T> failed(int code, String message) {
        return new JsonResult<>(code == 0 ? SERVER_ERROR_CODE : code, message, null);
    }
}
