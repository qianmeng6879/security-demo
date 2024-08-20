package top.mxzero.security.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/20
 */
@Component
public class EmailService {
    @Autowired
    private MailSender mailSender;
    @Autowired
    private MailProperties mailProperties;

    private final String EMAIL_CODE_TEMPLATE = "验证码为：%s，有效期：%s分钟，请妥善保管，如本次不是您本人亲自操作，请忽略该验证码。";

    @Async
    public void sendVerifyCode(String email, String code) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("验证码");
        message.setText(String.format(EMAIL_CODE_TEMPLATE, code, "15"));
        message.setFrom(String.format("验证码服务<%s>", mailProperties.getUsername()));
        mailSender.send(message);
    }
}
