package base;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class BaseServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    
    protected Object executeMethod(HttpServletRequest req,
            HttpServletResponse resp) throws Exception
    {
        System.out.println("请求的地址是："+req.getRequestURI());
        String method = HttpUtil.parseMthod(req);
        if (method == null || method.trim().equals(""))
        {
            method = "execute";
        }else if("ignore".equals(method)){
            return null;
        }
        Class cls = this.getClass();
        Method[] ms = cls.getMethods();
        Method m = cls.getDeclaredMethod(method,
                HttpServletRequest.class,
                HttpServletResponse.class);
        
        m.setAccessible(true);
        Object value = m.invoke(this, req, resp);
        return value;
        
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {
        try
        {
            Map<String, Object> param = req.getParameterMap();
            
            for (String key : param.keySet())
            {
            	System.out.println(param.get(key));
                req.setAttribute(key, req.getParameter(key));
            }
            
            executeMethod(req, resp);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            try{
                execute(req, resp);
            }catch(Exception el){
                el.printStackTrace();
            }
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {
        doPost(req, resp);
    }
    

    
    protected abstract void execute(HttpServletRequest req,
            HttpServletResponse resp) throws Exception;
    
    public String getContextPath(HttpServletRequest req)
    {
    	System.out.println(req.getContextPath());
        return req.getContextPath();
    }
    
}