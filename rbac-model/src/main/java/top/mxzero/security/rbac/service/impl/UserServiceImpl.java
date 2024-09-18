package top.mxzero.security.rbac.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.mxzero.common.exceptions.ServiceException;
import top.mxzero.security.rbac.service.UserService;
import top.mxzero.security.rbac.entity.User;
import top.mxzero.security.rbac.mapper.UserMapper;

/**
 * @author Peng
 * @since 2024/9/18
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;


    @Override
    @Transactional
    public boolean changePassword(String oldPwd, String newPwd, Long userId) {
        User user = userMapper.selectById(userId);

        if (!this.passwordEncoder.matches(oldPwd, user.getPassword())) {
            throw new ServiceException("原密码错误");
        }

        User updateUser = new User();
        updateUser.setId(userId);
        updateUser.setPassword(this.passwordEncoder.encode(newPwd));
        return this.userMapper.updateById(updateUser) > 0;
    }
}
