package top.mxzero.security.components;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/20
 */
@Slf4j
@Component
public class PhoneService {
    private final String SMS_CODE_TEMPLATE = "验证码为：%s，有效期：%s分钟，请妥善保管，请忽略该验证码，青忽略该验证码。";

    @Async
    public void sendVerifyCode(String phone, String code) {
        log.info("sms:{}:code:{}", phone, code);
    }
}
