package top.mxzero.endpoint.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.mxzero.common.dto.RestData;
import top.mxzero.endpoint.entity.Message;
import top.mxzero.endpoint.mapper.MessageMapper;
import top.mxzero.security.jwt.service.impl.JwtService;

import java.util.List;

/**
 * @author Peng
 * @since 2024/9/17
 */
@RestController
public class MessageController {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private MessageMapper mapper;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/message")
    public String echo(String msg) {
        Message message = new Message();
        message.setContent(msg);
        mapper.insert(message);
        return "【ECHO】" + msg;
    }

    @RequestMapping("/message/list")
    public RestData<List<Message>> messageListApi() {
        return RestData.success(mapper.selectList(new QueryWrapper<Message>().orderByAsc("id")));
    }

    @RequestMapping("/message/del/{id:\\d+}")
    public RestData<Boolean> removeMessageApi(@PathVariable("id") Long id) {
        return RestData.success(mapper.deleteById(id) > 0);
    }

    @RequestMapping("/message/del/batch")
    public RestData<Boolean> removeBatchMessageApi() {
        return RestData.success(mapper.delete(null) > 0);
    }

}
