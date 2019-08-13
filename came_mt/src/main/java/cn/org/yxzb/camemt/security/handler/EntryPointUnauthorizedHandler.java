package cn.org.yxzb.camemt.security.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @version V1.0.0
 * <p>
 *     SecurityContextHolder.getContext() 获得本次会话的上下文之后 其中并没有认证信息或者认证信息已经过时
 * </p>
 * @author  songhao sh_hq_123@163.com
 */
@Component
public class EntryPointUnauthorizedHandler implements AuthenticationEntryPoint {

  /**
   * 未登录或无权限时触发的操作
   * SecurityContextHolder.getContext() 中没有权限信息 或者权限失效 说明 没有携带 token 或者 token 无效
   * 返回  {"code":401,"message":"小弟弟，你没有携带 token 或者 token 无效！","data":""}
   * @param httpServletRequest
   * @param httpServletResponse
   * @param e
   * @throws IOException
   * @throws ServletException
   */
  @Override
  public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
      e.printStackTrace();
      //返回json形式的错误信息
      httpServletResponse.setCharacterEncoding("UTF-8");
      httpServletResponse.setContentType("application/json");

      httpServletResponse.getWriter().println("{\"code\":401,\"message\":\"小弟弟，你没有携带 token 或者 token 无效！\",\"data\":\"\"}");
      httpServletResponse.getWriter().flush();
  }

}
