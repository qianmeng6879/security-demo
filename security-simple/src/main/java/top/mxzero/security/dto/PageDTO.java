package top.mxzero.security.dto;

import lombok.Data;

import java.util.List;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/21
 */
@Data
public class PageDTO<T> {
    private List<T> records;
    private long totalPage;
    private long totalCount;
    private long currentPage;

    public PageDTO() {
    }

    public PageDTO(List<T> records, long totalCount, long pageSize, long currentPage) {
        this.currentPage = currentPage;
        this.totalCount = totalCount;
        this.records = records;
        this.total(totalCount, pageSize);
    }


    public void total(long totalCount, long pageSize) {
        this.totalCount = totalCount;
        this.totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
    }
}
