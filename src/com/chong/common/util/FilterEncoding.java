package com.chong.common.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class FilterEncoding implements Filter
{
    private FilterConfig filterConfig = null;
    
    public FilterEncoding()
    {
        
    }
    
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException
    {
        if (request.getCharacterEncoding() == null
                || !(request.getCharacterEncoding().equals("UTF-8")))
        {
            try
            {
                request.setCharacterEncoding("UTF-8");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        try
        {
            chain.doFilter(request, response);
        }
        catch (ServletException sx)
        {
            filterConfig.getServletContext().log(sx.getMessage());
        }
        catch (IOException iox)
        {
            filterConfig.getServletContext().log(iox.getMessage());
        }
    }
    
    public void destroy()
    {
    }
    
    public void init(FilterConfig filterConfig)
    {
        this.filterConfig = filterConfig;
    }
}