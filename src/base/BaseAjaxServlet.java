package base;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import base.bean.AjaxJsonBean;
import common.JacksonUtil;
import common.StringTools;

public abstract class BaseAjaxServlet extends BaseServlet
{    
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {

        execute(req, resp);        
        String json = (String)req.getAttribute("json");
        
        if(StringTools.isEmptyOrNone(json)){
            AjaxJsonBean jsonBean = new AjaxJsonBean();
            jsonBean.setSuccessCode("0");
            
            json = JacksonUtil.objToJson(jsonBean);
        }
        
        PrintWriter out;
        try
        {
            out = resp.getWriter();
            resp.setContentType("text/html");
            out.println(json);
            out.close();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
          
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {
        doPost(req, resp);
        
    }  
    
    
    
    
     
    
}
