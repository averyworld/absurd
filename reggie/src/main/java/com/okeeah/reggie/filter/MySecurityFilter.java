package com.okeeah.reggie.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.okeeah.reggie.constants.Global;
import com.okeeah.reggie.entity.Employee;
import com.okeeah.reggie.vo.R;
import org.springframework.util.AntPathMatcher;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @Description TODO
 * @Author SunYi okeeahsy@gmail.com
 * @Date 2023/4/25
 */
@WebFilter("/*")
public class MySecurityFilter implements Filter {

    private List<String> NOT_NEED_SECURITY_RESOURCES = new ArrayList<>();
    private AntPathMatcher antPathMatcher = new AntPathMatcher();


    //servlet中初始化添加放行的地址
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        NOT_NEED_SECURITY_RESOURCES.add("/backend/**");
        NOT_NEED_SECURITY_RESOURCES.add("/front/**");
        //后端用户登陆地址
        NOT_NEED_SECURITY_RESOURCES.add("/employee/login");
        NOT_NEED_SECURITY_RESOURCES.add("/employee/logout");
        //c端客户端用户登陆地址
        //NOT_NEED_SECURITY_RESOURCES.add("/user/sendMsg");
        //NOT_NEED_SECURITY_RESOURCES.add("/user/login");
        ////文档释放的静态资源
        //NOT_NEED_SECURITY_RESOURCES.add("/doc.html");
        //NOT_NEED_SECURITY_RESOURCES.add("/webjars/**");
        //NOT_NEED_SECURITY_RESOURCES.add("/swagger-resources");
        //NOT_NEED_SECURITY_RESOURCES.add("/v2/api-docs");

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse repose = (HttpServletResponse) servletResponse;

        String requestURI = req.getRequestURI();
        //System.out.println("请求路径是" + requestURI);

        //如果当前请求是静态资源，不管人家是否登陆 都可访问
        boolean need = needSecirity(requestURI);

        //如果当前请求访问资源 ，则需要进行判断是否登陆
        if (need) {
            Employee current = (Employee) req.getSession().getAttribute(Global.BACKEND_SESSION_SER_KEY);
            if (current == null) {
                R<Object> r = R.error(Global.EMPLOYEE_NOT_LOGIN);
                //纯servlet 需要设置编码
                repose.setContentType("application/json;charset=utf-8");
                //返回string字符
                repose.getWriter().println(new ObjectMapper().writeValueAsString(r));
                return;
            }else {
            //把当前用户的的id放进线程中
                SecurityContext.setCurrentId(current.getId());
            }

        }
        //放行请求
        filterChain.doFilter(req, repose);
        SecurityContext.removeCurrentId();
    }

    private boolean needSecirity(String requestURI) {
        for (String not_need_security_resource : NOT_NEED_SECURITY_RESOURCES) {
            if (antPathMatcher.match(not_need_security_resource, requestURI)) {
                return false;
            }
        }
        return true;
    }
}