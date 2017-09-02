package boot.service;

import boot.dto.LoginDto;
import boot.vo.LoginVo;

import javax.servlet.http.HttpSession;

/**
 * Created by huishen on 17/9/2.
 *
 */

public interface LoginService {

    LoginVo login(LoginDto loginDto, HttpSession session);

}
