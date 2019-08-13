package cn.org.yxzb.camemt.security.vo;

import cn.org.yxzb.camemt.pojo.TsUser;
import cn.org.yxzb.camemt.service.TsUserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.List;


/**
 * @version V1.0.0
 * <p>
 *     用于将 LoginUser 转换成 UserDetail对象，并且构建该用户所有的权限信息
 * </p>
 * @author songhao   weanyq@gmail.com
 * @date  2017年8月13日17:04:08
 */
@Component
public class SecurityModelFactory {

    private TsUserService userService;

    public SecurityModelFactory(TsUserService userService) {
        this.userService = userService;
    }

    public  UserDetailImpl create(TsUser user) {
        Collection<? extends GrantedAuthority> authorities;
        try {
            //需要实现 根据用户名获取其对应的所有权限
            List<String> permissions = userService.getAuthorities(user.getId());
            String[] ps = permissions.toArray(new String[0]);
            authorities = AuthorityUtils.createAuthorityList(ps);
        } catch (Exception e) {
            authorities = null;
        }

        Date lastPasswordReset = new Date();
        lastPasswordReset.setTime(user.getLastPasswordChange());
        return new UserDetailImpl(
                user.getUsername(),
                user.getUsername(),
                user.getPassword(),
                lastPasswordReset,
                authorities,
                user.enable()
        );
    }

}
