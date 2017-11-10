package ru.javawebinar.topjava.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.rmi.runtime.Log;

import javax.servlet.*;
import java.io.IOException;

public class UtfFilter implements Filter{
    private static final Logger LOG = LoggerFactory.getLogger(UtfFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            request.setCharacterEncoding("utf-8");
            filterChain.doFilter(request, response);
        }
        catch(ServletException sx) {
            LOG.error(sx.getMessage());
        }
        catch(IOException iox) {
            LOG.error(iox.getMessage());
        }
    }

    @Override
    public void destroy() {

    }
}
