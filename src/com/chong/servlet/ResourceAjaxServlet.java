package com.chong.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chong.DAO.ResourceDAO;
import com.chong.bean.ResourceBean;
import com.chong.common.base.BaseAjaxServlet;
import com.chong.common.util.JacksonUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class ResourceAjaxServlet extends BaseAjaxServlet
{
    private ResourceDAO resourceDAO = new ResourceDAO();
    

    
    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp)
    {

        
        String domain = (String)req.getParameter("domain");
        String type = (String)req.getParameter("type");
        
        System.out.println("domain  " +domain);
        
        ResourceBean bean = new ResourceBean();
        
        bean.setDomain(domain);
        bean.setType(type);
        List<ResourceBean> beans = resourceDAO.queryList(bean);
        
        try {
			req.setAttribute("json", JacksonUtil.objToJson(beans));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}      
        
    }
    
    
    public void add(HttpServletRequest req, HttpServletResponse resp){
    	String objJson = (req.getParameter("bean"));    	
    	ResourceBean resourceBean;
		try {
			resourceBean = (ResourceBean)JacksonUtil.jsonToObj(objJson, ResourceBean.class);
			resourceDAO.addBean(resourceBean);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    public void update(HttpServletRequest req, HttpServletResponse resp){
    	String objJson = (req.getParameter("bean"));    	
    	System.out.println("========="+objJson);
    	
    	ResourceBean resourceBean;
		try {
			resourceBean = (ResourceBean)JacksonUtil.jsonToObj(objJson, ResourceBean.class);
			resourceDAO.updateBean(resourceBean);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    
    public void delete(HttpServletRequest req, HttpServletResponse resp){
    	String id = req.getParameter("id");
    	
    	ResourceBean ResourceBean = new ResourceBean();
    	ResourceBean.setId(id);
    	resourceDAO.deleteBean(ResourceBean);
    }
    
}
