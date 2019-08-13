package cn.org.yxzb.camemt.controller;

import cn.org.yxzb.camemt.pojo.TsUser;
import cn.org.yxzb.camemt.security.service.LoginService;
import cn.org.yxzb.camemt.security.vo.LoginDetail;
import cn.org.yxzb.camemt.security.vo.TokenDetail;
import cn.org.yxzb.camemt.utils.Result;
import cn.org.yxzb.camemt.utils.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;

/**
 * @version V1.0.0
 * @Description 登陆接口
 * @Author liuyuequn weanyq@gmail.com
 * @Date 2017/10/3 1:30
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Value("${token.header}")
    private String tokenHeader;



    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result login(@Valid TsUser user, BindingResult bindingResult){
        // 检查有没有输入用户名密码和格式对不对
        if (bindingResult.hasErrors()){
            return new Result(false, StatusCode.LOGINERROR,"缺少参数或参数格式错误");
        }

        LoginDetail loginDetail = loginService.getLoginDetail(user.getUsername());


        String token = loginService.generateToken((TokenDetail) loginDetail);
        System.out.println(token);
        HashMap<String, String> map = new HashMap<>(1);
        map.put(tokenHeader, token);
        return new Result(true, StatusCode.OK,"登录成功",map);
    }



}
