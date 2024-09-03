package top.mxzero.security.jwt.dto;

/**
 * @author zero
 * @email qianmeng6879@163.com
 * @since 2022/11/13
 */
public enum TokenType {
    ACCESS_TOKEN("access_token"),
    REFRESH_TOKEN("refresh_token");
    private final String value;

    TokenType(String type) {
        this.value = type;
    }

    public String getValue() {
        return this.value;
    }
}
