package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.ResourceDAO;
import DAO.ResourceTgDAO;
import base.BaseAjaxServlet;
import bean.ResourceBean;
import bean.ResourceTgBean;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import common.JacksonUtil;
import common.StringTools;

public class ResourceTgAjaxServlet extends BaseAjaxServlet
{
    private ResourceTgDAO tgDAO = new ResourceTgDAO();
    private ResourceDAO resourceDAO = new ResourceDAO();
    

    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp)
    {
//    	 List<ResourceTgBean> rsBean = new ArrayList<ResourceTgBean>();
    	 
    	ResourceBean resourceBean = new ResourceBean();
    	List<ResourceBean> resourceBeans = resourceDAO.queryList(resourceBean); 
    	
    	Map<String, ResourceTgBean> resourceMap = new HashMap<String, ResourceTgBean>();
    	String resourceIds = "";
    	for(ResourceBean tempR:resourceBeans){
    		resourceIds = resourceIds + tempR.getId() + ",";   
    		
    		ResourceTgBean rtBean = new ResourceTgBean();    		
    		rtBean.setResourceId(tempR.getId());
    		rtBean.setResourceDomain(tempR.getDomain());
    		rtBean.setRegisterState(tempR.getRegisterState());    	
    		rtBean.setResourceTypeName(tempR.getType());
    		
    		resourceMap.put(tempR.getId(), rtBean);
    	}
    	
    	String tgId =req.getParameter("tgId");    	
        ResourceTgBean bean = new ResourceTgBean();
        bean.setTgId(tgId);  
        bean.setResourceIds(resourceIds);
        List<ResourceTgBean> beans = tgDAO.queryList(bean);        
        
       
        for(ResourceTgBean tempR:beans){
        	ResourceTgBean resourceTgBean = resourceMap.get(tempR.getResourceId());
        	if(resourceTgBean==null){
        		resourceTgBean = new ResourceTgBean();
        	}
        	
        	tempR.setResourceId(resourceTgBean.getResourceId());
        	tempR.setResourceDomain(resourceTgBean.getResourceDomain());
        	tempR.setRegisterState(resourceTgBean.getRegisterState()); 
        	
        	resourceMap.put(tempR.getResourceId(), tempR);
        }

        
        
        try {
//        	System.out.println( JacksonUtil.objToJson(ajaxJsonBean));
			req.setAttribute("json", JacksonUtil.objToJson(resourceMap.values()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}      
        
    }
    
    public void add(HttpServletRequest req, HttpServletResponse resp){
    	String objJson = (req.getParameter("bean"));
    	
    	System.out.println("bean==>" +objJson);
    	
    	ResourceTgBean bean;
		try {
			bean = (ResourceTgBean)JacksonUtil.jsonToObj(objJson, ResourceTgBean.class);
			
			String tgId =req.getParameter("tgId");    	
			bean.setTgId(tgId);
			
			if (StringTools.isEmptyOrNone(bean.getId())) {
				tgDAO.addBean(bean);
			} else {
				tgDAO.updateBean(bean);
			}
			
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
    	add(req,resp);
    	
    }
    
    
    public void delete(HttpServletRequest req, HttpServletResponse resp){
    	String id = req.getParameter("id");
    	
    	ResourceTgBean ResourceTgBean = new ResourceTgBean();
    	ResourceTgBean.setId(id);
    	tgDAO.deleteBean(ResourceTgBean);
    }
    
}
