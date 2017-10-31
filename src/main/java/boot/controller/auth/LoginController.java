package boot.controller.auth;

import boot.dto.LoginDto;
import boot.service.LoginService;
import boot.util.ValidateCodeUtils;
import boot.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by huishen on 17/9/2.
 * 登录功能
 */

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    // 对某一个请求处理跨域
    // @CrossOrigin(origins = "http://localhost:9000")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public LoginVo login(@RequestBody LoginDto loginDto, HttpSession session) {
        LoginVo login = loginService.login(loginDto, session);
        return login;
    }

    /**
     * 图形验证码
     */
    @RequestMapping(path = "/valid_code", method = {RequestMethod.POST, RequestMethod.GET})
    public void validCode(HttpServletRequest request, HttpServletResponse response) {
        // 返回一个图片
        BufferedImage buffImg = ValidateCodeUtils.createCode();
        try {
            response.setContentType("image/png");
            ImageIO.write(buffImg, "png", response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException e) {
            // 日志记录
            e.printStackTrace();
        }

    }

    @RequestMapping(path = "/error")
    public void loginError() throws Exception {
        throw new Exception("未登录");
    }


}
