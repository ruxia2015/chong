package com.chong.common.util;


import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
 
public class EncodingFilter implements Filter {
     
    private static String encoding;
    private static final String DEFAULT_CHARSET="UTF-8";
    @Override
    public void destroy() {
        System.out.println("--------destroy---------");
    }
 
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
//        System.out.println("--------doFilter---------");
        HttpServletRequest httpRequest = (HttpServletRequest) request;  
        HttpServletResponse httpResponse=(HttpServletResponse)response;
        if("GET".equals(httpRequest.getMethod())){
            EncodingHttpServletRequest wrapper = new EncodingHttpServletRequest(httpRequest, encoding);   
            chain.doFilter(wrapper, response);  
        }else{
            httpRequest.setCharacterEncoding(encoding);
            httpResponse.setContentType("text/html;charset="+encoding);
            chain.doFilter(request, response);  
        }
    }
    private static class  EncodingHttpServletRequest extends HttpServletRequestWrapper{
         private HttpServletRequest request;
         public EncodingHttpServletRequest(HttpServletRequest request,String encoding) {
           super(request);
           this.request = request;
         }
         @Override
        public String getParameter(String name) {
               String value = request.getParameter(name);
               if(StringTools.isEmptyOrNone(value)){
            	   System.out.println("getParameter==> "+name);
            	   return null;
               }
               try {
                value = new String(value.getBytes("iso8859-1"), encoding);
                return value;
               } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
               }
               return super.getParameter(name);
        }
    }
    @Override
    public void init(FilterConfig config) throws ServletException {
         System.out.println("--------init---------");  
         encoding = config.getInitParameter("encoding");
         if(encoding==null||"".equals(encoding))
             encoding=DEFAULT_CHARSET;
    }
 
}