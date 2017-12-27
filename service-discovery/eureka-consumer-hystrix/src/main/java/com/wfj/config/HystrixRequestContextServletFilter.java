package com.wfj.config;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author wjg
 * @date 2017/12/22 15:15
 */
@WebFilter(filterName = "hystrixRequestContextServletFilter",urlPatterns = "/*")
public class HystrixRequestContextServletFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(HystrixRequestContextServletFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("过滤器开始。。。");
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
       try{
           filterChain.doFilter(servletRequest,servletResponse);
       }finally {
           context.close();
       }
        logger.info("过滤器结束。。。");
    }

    @Override
    public void destroy() {

    }
}
