package com.chong.common.base;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chong.common.base.bean.AjaxJsonBean;
import com.chong.common.util.JacksonUtil;
import com.chong.common.util.StringTools;

public abstract class BaseAjaxServlet extends BaseServlet
{    
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {
        
        Map<String, Object> param = req.getParameterMap();
        


    	try {
			executeMethod(req, resp);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}        
        String json = (String)req.getAttribute("json");
        
        System.out.println(json);
        
        if(StringTools.isEmptyOrNone(json)){
            AjaxJsonBean jsonBean = new AjaxJsonBean();
            jsonBean.setSuccessCode("0");
            
            json = JacksonUtil.objToJson(jsonBean);
        }
        
        PrintWriter out;
        try
        {
        	resp.setContentType("text/html;charset=utf-8");
            out = resp.getWriter();
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