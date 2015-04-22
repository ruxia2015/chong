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
        String method = HttpUtil.parseMthod(req);
        if (method == null || method.trim().equals(""))
        {
            method = "execute";
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
                req.setAttribute(key, param.get(key));
            }
            
            executeMethod(req, resp);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            execute(req, resp);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {
        doPost(req, resp);
    }
    
    protected abstract void execute(HttpServletRequest req,
            HttpServletResponse resp);
    
    
    public String getContextPath(HttpServletRequest req)
    {
        return req.getContextPath();
    }
    
}