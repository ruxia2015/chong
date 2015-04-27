package com.chong.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chong.DAO.ResourceTypeDAO;
import com.chong.bean.ResourceTypeBean;
import com.chong.common.base.BaseAjaxServlet;
import com.chong.common.util.JacksonUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class ResourceTypeAjaxServlet extends BaseAjaxServlet
{
    private ResourceTypeDAO resourceTypeDAO = new ResourceTypeDAO();
    

    
    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp)
    {
        ResourceTypeBean bean = new ResourceTypeBean();
        
        List<ResourceTypeBean> beans = resourceTypeDAO.queryList(bean);
        
        try {
			req.setAttribute("json", JacksonUtil.objToJson(beans));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}      
        
    }
    
    public void add(HttpServletRequest req, HttpServletResponse resp){
    	String objJson = (req.getParameter("bean"));    	
    	ResourceTypeBean bean;
		try {
			bean = (ResourceTypeBean)JacksonUtil.jsonToObj(objJson, ResourceTypeBean.class);
			resourceTypeDAO.addBean(bean);
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
    	ResourceTypeBean bean;
		try {
			bean = (ResourceTypeBean)JacksonUtil.jsonToObj(objJson, ResourceTypeBean.class);
			resourceTypeDAO.updateBean(bean);
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
    	
    	ResourceTypeBean resourceTypeBean = new ResourceTypeBean();
    	resourceTypeBean.setId(id);
    	resourceTypeDAO.deleteBean(resourceTypeBean);
    }
    
}
