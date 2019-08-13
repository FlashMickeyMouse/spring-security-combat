package cn.org.yxzb.camemt.security.filter;


import cn.org.yxzb.camemt.utils.TokenUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <p>
 *    解析 token 的过滤器
 *    配置在 Spring Security 的配置类中
 *    用于解析 token ，将用户所有的权限写入本次 Spring Security 的会话中
 *    一句话表述就是 在流程进入逻辑之前构建Spring Security 的会话
 * </p>
 * @version V1.0.0
 * @author  songhao
 * @date  2017年8月11日10:59:57
 */
public class AuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * json web token 在请求头的名字
     */
    private String tokenHeader;

    /**
     * 辅助操作 token 的工具类
     */
    private TokenUtils tokenUtils;

    /**
     * Spring Security 的核心操作服务类
     * 在当前类中将使用 UserDetailsService 来获取 userDetails 对象
     */
    private UserDetailsService userDetailsService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 将 ServletRequest 转换为 HttpServletRequest 才能拿到请求头中的 token
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        System.out.println(((HttpServletRequest) request).getServletPath());
        // 尝试获取请求头的 token
        String authToken = httpRequest.getHeader(this.tokenHeader);
        // 尝试拿 token 中的 username
        // 若是没有 token 或者拿 username 时出现异常，那么 username 为 null
        String username = this.tokenUtils.getUsernameFromToken(authToken);

        // 如果上面解析 token 成功并且拿到了 username 并且本次会话的权限还未被写入 ，每次回话都需要写入一次，如果没有写入将会被后面的过滤器链判断为没有经过身份认证
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 用 UserDetailsService 从数据库中拿到用户的 UserDetails 类
            // UserDetails 类是 Spring Security 用于保存用户权限的实体类
            // 通过username查询出用户的信息和用户对应的权限们，将其封装为 userDetails
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            // 检查用户带来的 token 是否有效
            // token 是否过期， token 生成时间是否在最后一次密码修改时间之前
            // 若是检查通过
            if (this.tokenUtils.validateToken(authToken, userDetails)) {
                // 生成通过认证
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                // 将权限写入本次会话  注意这是本次回话，Security提供的本次回话的域，因为我们的鉴权机制是jwt实现的所以每次访问web资源都要通过这一步进行会话构建
                //经过这一步之后会话上下文中就包含了当前用户 以及其权限信息 然后就可以通过注解进行 鉴权了
                //由于每次会话的构建都会从数据库查询当前用户的信息和其对应的权限信息，所以依据此原理就可以实现动态的权限校验了
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            if (!userDetails.isEnabled()){
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().print("{\"code\":\"452\",\"data\":\"\",\"message\":\"账号处于黑名单\"}");
                return;
            }
        }

        chain.doFilter(request, response);
    }



    public void setTokenHeader(String tokenHeader) {
        this.tokenHeader = tokenHeader;
    }



    public void setTokenUtils(TokenUtils tokenUtils) {
        this.tokenUtils = tokenUtils;
    }



    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
