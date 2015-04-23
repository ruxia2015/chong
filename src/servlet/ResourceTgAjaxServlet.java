package servlet;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.ResourceTgService;
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
    
    private ResourceTgService resourceTgService = new ResourceTgService();
    
    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp)
    {
        //    	 List<ResourceTgBean> rsBean = new ArrayList<ResourceTgBean>();
        
        ResourceBean resourceBean = new ResourceBean();
        String tgId =req.getParameter("tgId");    
        Collection<ResourceTgBean> beans = resourceTgService.queryResourceTgList(tgId, resourceBean);
        
        try
        {
            //        	System.out.println( JacksonUtil.objToJson(ajaxJsonBean));
            req.setAttribute("json", JacksonUtil.objToJson(beans));
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    public void add(HttpServletRequest req, HttpServletResponse resp)
    {
        String objJson = (req.getParameter("bean"));
        
        System.out.println("bean==>" + objJson);
        
        ResourceTgBean bean;
        try
        {
            bean = (ResourceTgBean) JacksonUtil.jsonToObj(objJson,
                    ResourceTgBean.class);
            
            String tgId = req.getParameter("tgId");
            bean.setTgId(tgId);
            
            if (StringTools.isEmptyOrNone(bean.getId()))
            {
                tgDAO.addBean(bean);
            }
            else
            {
                tgDAO.updateBean(bean);
            }
            
        }
        catch (JsonParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (JsonMappingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (InstantiationException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    public void update(HttpServletRequest req, HttpServletResponse resp)
    {
        add(req, resp);
        
    }
    
    public void delete(HttpServletRequest req, HttpServletResponse resp)
    {
        String id = req.getParameter("id");
        
        ResourceTgBean ResourceTgBean = new ResourceTgBean();
        ResourceTgBean.setId(id);
        tgDAO.deleteBean(ResourceTgBean);
    }
    
}
