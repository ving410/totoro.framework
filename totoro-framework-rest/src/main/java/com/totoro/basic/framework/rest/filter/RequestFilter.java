package com.totoro.basic.framework.rest.filter;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 过滤器
 *
 * @author Maleah
 * @create 2017-09-27 15:10
 **/
@Slf4j
public class RequestFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Stopwatch sp = Stopwatch.createStarted();

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String path = httpRequest.getRequestURI();
        String queryStr = httpRequest.getQueryString();
        if (queryStr != null) {
            path += "?" + httpRequest.getQueryString();
        }
        try{
            filterChain.doFilter(servletRequest,servletResponse);
        }finally {
            log.info("access url [{}], cost time [{}] ms )", path, sp.elapsed(TimeUnit.MILLISECONDS));
        }

    }

    @Override
    public void destroy() {

    }
}
