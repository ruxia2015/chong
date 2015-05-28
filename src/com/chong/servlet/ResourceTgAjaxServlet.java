package com.chong.servlet;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chong.DAO.ResourceTgDAO;
import com.chong.bean.ResourceBean;
import com.chong.bean.ResourceTgBean;
import com.chong.common.base.BaseAjaxServlet;
import com.chong.common.util.JacksonUtil;
import com.chong.common.util.StringTools;
import com.chong.service.ResourceTgService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class ResourceTgAjaxServlet extends BaseAjaxServlet
{
    private ResourceTgDAO tgDAO = new ResourceTgDAO();
    
    private ResourceTgService resourceTgService = new ResourceTgService();
    
    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp)
    {
        
        ResourceBean resourceBean = new ResourceBean();
        String tgId = req.getParameter("tgId");
        Collection<ResourceTgBean> beans = resourceTgService.queryResourceTgList(tgId,
                resourceBean);
        
        try
        {
            req.setAttribute("json", JacksonUtil.objToJson(beans));
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    public void add(HttpServletRequest req, HttpServletResponse resp)
            throws Exception
    {
        String objJson = (req.getParameter("bean"));
        
        System.out.println("bean==>" + objJson);
        
        ResourceTgBean bean = (ResourceTgBean) JacksonUtil.jsonToObj(objJson,
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
    
    public void update(HttpServletRequest req, HttpServletResponse resp)
            throws Exception
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
