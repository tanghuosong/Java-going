package com.rbac.utils;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Charlot on 2017/8/21.
 */
public class EncodingFilter extends HttpServlet implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        String loginUrl = "/user/login";
        String authUrl = "/user/auth";
        String unAuthorizedUrl = "/user/unAuthorized";
        // 过滤字符集
        if (!response.getCharacterEncoding().equals("UTF-8")) {
            response.setCharacterEncoding("UTF-8");
        }
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        // 请求的api
        String requestUrl = httpServletRequest.getRequestURI();
        // 请求的api
        String requestMethod = httpServletRequest.getMethod();

        System.out.println("请求API:"+requestUrl+";请求方法："+requestMethod);
        // 登录的api、未登录提示api、未授权api 直接跳过
        if(loginUrl.equals(requestUrl) || authUrl.equals(requestUrl) || unAuthorizedUrl.equals(requestUrl)){
            filterChain.doFilter(request,response);
            return;
        }else{
            // 获取base64 头
            String token = httpServletRequest.getHeader("Authorization");
            if(token == null){
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+unAuthorizedUrl);
                return;
            }else{
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+authUrl+"?token="+token);
                return;
            }
        }

    }

    @Override
    public void destroy() {
    }
}