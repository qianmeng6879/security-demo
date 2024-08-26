package top.mxzero.security.validate.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import top.mxzero.security.validate.FieldMatch;

import java.lang.reflect.Field;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/26
 */
public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        this.firstFieldName = constraintAnnotation.first();
        this.secondFieldName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            // 通过反射获取字段值
            Object firstValue = getFieldValue(value, firstFieldName);
            Object secondValue = getFieldValue(value, secondFieldName);

            // 比较字段值
            return firstValue == null && secondValue == null || firstValue != null && firstValue.equals(secondValue);
        } catch (Exception e) {
            // 如果发生异常，验证失败
            return false;
        }
    }

    private Object getFieldValue(Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        // 获取类的字段
        Field field = object.getClass().getDeclaredField(fieldName);
        // 设置字段可访问
        field.setAccessible(true);
        // 返回字段值
        return field.get(object);
    }
}
