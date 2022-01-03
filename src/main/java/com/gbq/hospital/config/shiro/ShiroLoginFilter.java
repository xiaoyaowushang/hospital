package com.gbq.hospital.config.shiro;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author Morty
 */
public class ShiroLoginFilter extends FormAuthenticationFilter {

    /**
     * 在访问controller前判断是否登录，返回json，不进行重定向。
     * @param request
     * @param response
     * @return true-继续往下执行，false-该filter过滤器已经处理，不继续执行其他过滤器
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        if (isAjax(request)) {
            HashMap<String,Object> result = new HashMap<>();
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json");
            result.put("msg","未登录");
            result.put("status",-1);
            httpServletResponse.getWriter().write(JSONObject.toJSON(result).toString());
        }/* else {
            *//**
             * @Mark 非ajax请求重定向为登录页面
             *//*
            httpServletResponse.sendRedirect("/gbq_blog/login/noLogin");
        }*/
        return false;
    }

    private boolean isAjax(ServletRequest request) {
        String header = ((HttpServletRequest) request).getHeader("X-Requested-With");
        return "XMLHttpRequest".equalsIgnoreCase(header);
    }

}
