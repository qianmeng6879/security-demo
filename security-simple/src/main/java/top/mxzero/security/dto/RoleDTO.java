package top.mxzero.security.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/21
 */
@Data
public class RoleDTO {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    @NotBlank
    @Length(max = 30)
    private String name;
    @NotNull
    @Size(min = 1)
    private List<String> permissionIds;
}
