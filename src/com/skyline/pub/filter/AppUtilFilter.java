package com.skyline.pub.filter;

import com.skyline.pub.utils.AppUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: skyline
 * Date: 12-8-29
 * Time: 上午10:59
 * 目的是把HttpServletRequest、HttpServletResponse、ServletContext 绑定该次请求中，
 * 虽然struts2 提供了 ServletRequestAware 接口，但是需要实现该接口
 * 使用这个filter,我可以在任意位置(包括 action service 和 dao)直接调用AppUtils 中的静态方法来获取
 */
public class AppUtilFilter implements Filter {
    private  FilterConfig filterConfig = null;
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        AppUtils.setRequest(request);
        AppUtils.setResponse(response);
        AppUtils.setServletContext(filterConfig.getServletContext());
        filterChain.doFilter(servletRequest,servletResponse);
    }

    public void destroy() {
        AppUtils.removeThreadLocal();
    }
}
