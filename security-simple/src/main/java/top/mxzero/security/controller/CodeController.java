package top.mxzero.security.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.mxzero.security.components.EmailService;
import top.mxzero.security.components.PhoneService;
import top.mxzero.security.dto.RestData;
import top.mxzero.security.exceptions.ServiceCode;
import top.mxzero.security.utils.EmailUtil;
import top.mxzero.security.utils.PhoneUtil;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/7/31
 */
@Slf4j
@RestController
public class CodeController {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private PhoneService phoneService;
    @Autowired
    private EmailService emailService;

    @GetMapping(value = "/code/image", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> imageCodeApi(@RequestParam String flag) {
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(100, 40, 4, 20);
        String code = captcha.getCode().toLowerCase();
        redisTemplate.opsForValue().set("code:image:" + flag, code, 120, TimeUnit.SECONDS);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(captcha.getImageBytes(), headers, HttpStatus.OK);
    }

    @GetMapping(value = "/code/email")
    public RestData<?> emailCodeApi(@RequestParam String email) {
        if (!EmailUtil.isEmail(email)) {
            return RestData.error(ServiceCode.EMAIL_INVALID);
        }

        Long expireTime = redisTemplate.getExpire("code:email:" + email, TimeUnit.SECONDS);
        // 5分钟内不允许重复获取验证码
        if (expireTime != null && expireTime > 10 * 60) {
            return RestData.error(ServiceCode.CODE_REPETITION);
        }

        String code = String.valueOf(ThreadLocalRandom.current().nextInt(1000, 9999));
        redisTemplate.opsForValue().set("code:email:" + email, code, 15, TimeUnit.MINUTES);
        emailService.sendVerifyCode(email, code);

        return RestData.success(true, "验证码发送成功");
    }

    @GetMapping(value = "/code/phone")
    public RestData<?> phoneCodeApi(@RequestParam String phone) {
        if (!PhoneUtil.isPhone(phone)) {
            return RestData.error(ServiceCode.PHONE_INVALID);
        }


        Long expireTime = redisTemplate.getExpire("code:phone:" + phone, TimeUnit.SECONDS);
        // 5分钟内不允许重复获取验证码
        if (expireTime != null && expireTime > 10 * 60) {
            return RestData.error(ServiceCode.CODE_REPETITION);
        }

        String code = String.valueOf(ThreadLocalRandom.current().nextInt(1000, 9999));
        redisTemplate.opsForValue().set("code:phone:" + phone, code, 15, TimeUnit.MINUTES);
        phoneService.sendVerifyCode(phone, code);

        return RestData.success(true, "验证码发送成功");
    }

}
