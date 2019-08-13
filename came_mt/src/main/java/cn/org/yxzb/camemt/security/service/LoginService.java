package cn.org.yxzb.camemt.security.service;

import cn.org.yxzb.camemt.security.vo.LoginDetail;
import cn.org.yxzb.camemt.security.vo.TokenDetail;
import cn.org.yxzb.camemt.service.TsUserService;
import cn.org.yxzb.camemt.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @version V1.0.0
 * @author  songhao sh_hq_123@163.com
 * @date  2019.08.13
 */
@Service
public class LoginService {

    @Autowired
    private TsUserService userService;
    @Autowired
    private TokenUtils tokenUtils;


    public LoginDetail getLoginDetail(String username) {
        return userService.getUserByUsername(username);
    }

    public String generateToken(TokenDetail tokenDetail) {
        return tokenUtils.generateToken(tokenDetail);
    }
}
