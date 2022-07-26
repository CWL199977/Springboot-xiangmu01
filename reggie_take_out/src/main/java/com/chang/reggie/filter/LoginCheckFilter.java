package com.chang.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.chang.reggie.common.BaseContext;
import com.chang.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 检查用户是否登录的过滤器
 * 参数1：拦截的名字
 * 参数2：拦截的条件
 */
@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    //路径匹配器
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request1 = (HttpServletRequest) request;
        HttpServletResponse response1 = (HttpServletResponse) response;

        //1.获取请求的url
        String requestURI = request1.getRequestURI();
        log.info("拦截到请求: {}",requestURI);

        //获取不需要处理的请求路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**"
        };


        //2.判断请求的url是否需要处理
        boolean check = check(urls, requestURI);

        //3.如果不需要处理，则放行
        if (check) {
            log.info("本次请求不需要处理: {}",requestURI);
            chain.doFilter(request1, response1);
            return;
        }

        //4.判断用户是否登录，如果已经登录，则放行
        if (request1.getSession().getAttribute("employee") != null) {
            log.info("用户已登录，用户ID为: {}",request1.getSession().getAttribute("employee"));
            Long empId= (Long) request1.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);
            chain.doFilter(request1, response1);
            return;
        }
        log.info("用户未登录");
        //5.如果没有登录,则返回未登录结果,通过输出流向客户端页面响应数据
        response1.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    /**
     * 路径匹配，检查本次请求是否需要放行
     *
     * @return
     */
    public boolean check(String[] urls, String requestURI) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match) {
                return true;
            }
        }
        return false;

    }
}
