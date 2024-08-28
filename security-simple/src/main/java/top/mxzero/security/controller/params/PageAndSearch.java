package top.mxzero.security.controller.params;

import lombok.Data;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/28
 */
@Data
public class PageAndSearch {
    private Long page = 1L;
    private Long size = 10L;
    private String search;
}
