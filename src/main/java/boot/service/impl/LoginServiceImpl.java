package boot.service.impl;

import boot.domain.User;
import boot.dto.LoginDto;
import boot.mapper.UserMapper;
import boot.service.LoginService;
import boot.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpSession;

/**
 * Created by huishen on 17/9/2.
 *
 */

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public LoginVo login(LoginDto loginDto, HttpSession session) {

        String name = loginDto.getName();
        String password = loginDto.getPassword();

        User user = userMapper.selectByName(name);
        if(ObjectUtils.isEmpty(user)) {
            throw new IllegalArgumentException("此用户没有注册");
        } else if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("密码错误");
        }

        session.setAttribute("userId", user.getId());
        session.setMaxInactiveInterval(60 * 60 * 2);   // 2小时过期

        return LoginVo.builder()
            .userId(user.getId())
            .name(user.getName())
            .token(session.getId())
            .build();

    }

}
