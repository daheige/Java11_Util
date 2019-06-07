package xyz.cvzm.util.http.enums;

/**
 * headers 中常见的 Content-Type 枚举
 */
public enum ContentType {

    APPLICATION_JSON("application/json"),

    APPLICATION_FROM("application/x-www-form-urlencoded"),

    MULTIPART("multipart/form-data"),

    TEXT("text/xml"),

    ;

    private String type;

    ContentType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
