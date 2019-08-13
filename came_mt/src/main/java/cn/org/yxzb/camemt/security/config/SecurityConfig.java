package cn.org.yxzb.camemt.security.config;

import cn.org.yxzb.camemt.security.filter.AuthenticationTokenFilter;
import cn.org.yxzb.camemt.security.handler.EntryPointUnauthorizedHandler;
import cn.org.yxzb.camemt.security.handler.MyAccessDeniedHandler;
import cn.org.yxzb.camemt.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;


/**
 *<p>
 *     该类是security的配置类
 *     prePostEnabled is true 可以在方法上使用注解进行权限拦截
 *</p>
 *
 * @author songhao
 * @see EnableGlobalMethodSecurity#prePostEnabled()
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    /**
     * 注册 401 处理器  SecurityContextHolder.getContext() 中没有权限信息 或者权限失效
     */
    @Autowired
    private EntryPointUnauthorizedHandler unauthorizedHandler;

    /**
     * 注册 403 处理器  无权限
     */
    @Autowired
    private MyAccessDeniedHandler accessDeniedHandler;



    /**
     * json web token 在请求头的名字
     */
    @Value("${token.header}")
    private String tokenHeader;

    /**
     * 辅助操作 token 的工具类
     */
    @Autowired
    private TokenUtils tokenUtils;

    /**
     * Spring Security 的核心操作服务类
     * 在当前类中将使用 UserDetailsService 来获取 userDetails 对象
     */
    @Resource
    private UserDetailsService userDetailsService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .antMatchers("/auth").authenticated()     //访问此资源需要经过身份认证 ，也就是需要将 认证信息写入 SecurityContextHolder.getContext().setAuthentication(authentication);
                .antMatchers("/admin/*").hasAuthority("admin")   //  经过身份认证 但是 需拥有 admin 这个权限
//                .antMatchers("/ADMIN").hasRole("ADMIN")     // 需拥有 ADMIN 这个身份
                .anyRequest().permitAll()       // 允许所有请求通过  这一步是让 一些没有参与被拦截的url也可以通过 例如静态资源或者/login
                .and()
                // 配置被拦截时的处理
                .exceptionHandling()
                .authenticationEntryPoint(this.unauthorizedHandler)   // 添加 token 无效或者没有携带 token 时的处理
                .accessDeniedHandler(this.accessDeniedHandler)      //添加无权限时的处理
                .and()
                .csrf()
                .disable()                      // 禁用 Spring Security 自带的跨域处理
                .sessionManagement()                        // 定制我们自己的 session 策略
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 调整为让 Spring Security 不创建和使用 session


        /**
         * 本次 json web token 权限控制的核心配置部分
         * 在 Spring Security 开始判断本次会话是否有权限时的前一瞬间
         * 通过添加过滤器将 token 解析，将用户所有的权限写入本次会话
         */
        AuthenticationTokenFilter authenticationTokenFilter = new AuthenticationTokenFilter();
        authenticationTokenFilter.setTokenHeader(tokenHeader);
        authenticationTokenFilter.setTokenUtils(tokenUtils);
        authenticationTokenFilter.setUserDetailsService(userDetailsService);
        authenticationTokenFilter.setAuthenticationManager(authenticationManagerBean());
        http.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
