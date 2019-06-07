package xyz.cvzm.util.http.enums;

/**
 * 请求方法类型枚举
 */
public enum Method {

    GET("GET"),

    POST("POST"),

    PUT("PUT"),

    DELETE("DELETE"),

    ;

    private String method;


    Method(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }


}
