package cn.org.yxzb.camemt.security.service;

import cn.org.yxzb.camemt.pojo.TsUser;
import cn.org.yxzb.camemt.security.vo.SecurityModelFactory;
import cn.org.yxzb.camemt.service.TsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @version V1.0.0
 * <p>
 *     Spring Security 用于将 数据库中的用户信息转换成 userDetail 对象的服务类的实现类
 * </p>
 * @author songhao
 * @date  2018.08.13
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private TsUserService userService;

    @Autowired
    private SecurityModelFactory securityModelFactory;


    /**
     * 获取 userDetail
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        TsUser user = userService.getUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return securityModelFactory.create(user);
        }
    }
}
