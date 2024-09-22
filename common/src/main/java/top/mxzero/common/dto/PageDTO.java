package top.mxzero.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Peng
 * @since 2024/8/21
 */
@Data
public class PageDTO<T> {
    @JsonProperty("records")
    private List<T> records;
    @JsonProperty("total_page")
    private long totalPage;
    @JsonProperty("total_count")
    private long totalCount;
    @JsonProperty("current_page")
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
