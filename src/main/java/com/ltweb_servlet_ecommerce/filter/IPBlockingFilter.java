package com.ltweb_servlet_ecommerce.filter;

import com.ltweb_servlet_ecommerce.cacheMemory.BlockedIPCache;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class IPBlockingFilter implements Filter {

    private  ServletContext context;
    private BlockedIPCache ipCache = BlockedIPCache.getInstance();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.context = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        if (!ipCache.isBlocked(ipAddress)) {
            filterChain.doFilter(servletRequest, servletResponse); // Pass request along the filter chain
        } else {
            response.getWriter().println("You are blocked.");
        }
    }

}

