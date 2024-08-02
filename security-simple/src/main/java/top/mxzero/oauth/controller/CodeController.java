package top.mxzero.oauth.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/7/31
 */
@Slf4j
@Controller
public class CodeController {
    @GetMapping(value = "/code/image", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> imageCodeApi(HttpSession session) {
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(100, 40, 4, 20);
        String code = captcha.getCode();
        session.setAttribute("imageCode", code);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(captcha.getImageBytes(), headers, HttpStatus.OK);
    }
}
