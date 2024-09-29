package com.ltweb_servlet_ecommerce.filter;


import com.ltweb_servlet_ecommerce.utils.IPAddressHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class IPAddressFilter implements Filter {
    private  ServletContext context;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.context = filterConfig.getServletContext();
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String ipAddress;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        try {
            ipAddress = request.getHeader("X-FORWARDED-FOR");
            if (ipAddress == null) {
                ipAddress = request.getRemoteAddr();
            }
        } catch (Exception e) {
            ipAddress = "Invalid IP:" + e.getMessage();
        }
        IPAddressHolder.setIPAddress(ipAddress);
        chain.doFilter(request, response);
        IPAddressHolder.removeIPAddress();
    }
}

