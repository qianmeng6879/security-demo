package top.mxzero.endpoint.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.mxzero.endpoint.entity.Message;

/**
 * @author Peng
 * @since 2024/9/21
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {
}
