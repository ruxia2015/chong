package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import DAO.ResourceDAO;
import base.BaseAjaxServlet;
import bean.ResourceBean;

import common.JacksonUtil;

public class ResourceAjaxServlet extends BaseAjaxServlet
{
    private ResourceDAO resourceDAO = new ResourceDAO();
    
    @Override
    protected Object getObject()
    {
        return new ResourceAjaxServlet();
    }
    
    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp)
    {
        ResourceBean bean = new ResourceBean();
        
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
