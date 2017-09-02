package boot.controller.auth;

import boot.dto.LoginDto;
import boot.service.LoginService;
import boot.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by huishen on 17/9/2.
 * 登录功能
 */

@RestController
@RequestMapping("")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public LoginVo login(@RequestBody LoginDto loginDto, HttpSession session) {
        LoginVo login = loginService.login(loginDto, session);
        return login;
    }

    @RequestMapping(path = "/login/error")
    public void loginError() throws Exception {
        throw new Exception("未登录");
    }


}
