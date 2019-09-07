package cn.itcast.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //0.强制转换
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        
        //获取资源请求路径
        String uri = request.getRequestURI();

        //判断是否包含登陆相关资源路径,要注意排除掉 css/js/图片/验证码等资源
        if(uri.contains("/login.jsp") || uri.contains("/loginServlet") || uri.contains("/css/") || uri.contains("/js/") || uri.contains("/fonts/") || uri.contains("/checkCodeServlet")){
            //包含，用户就是想登陆，放行
            chain.doFilter(req,resp);
        }else{
            //不包含，需要验证用户是否登陆
            //3.从session中获取user
            Object user = request.getSession().getAttribute("user");
            if(user != null){
                //登陆了，放行
                chain.doFilter(req,resp);
            }else{
                //没有登陆，跳转登陆页面
                request.setAttribute("login_msg", "您尚未登陆，请登陆");
                request.getRequestDispatcher("/login.jsp").forward(request,resp);
            }
        }


        //chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

    public void destroy() {

    }

}
