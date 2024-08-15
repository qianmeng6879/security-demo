package top.mxzero.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/15
 */
@Controller
public class EchoPageController {
    @RequestMapping("/echo")
    public String echoPage(){
        return "echo";
    }
}
