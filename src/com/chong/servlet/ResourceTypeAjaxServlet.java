package com.chong.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chong.DAO.ResourceDAO;
import com.chong.DAO.ResourceTypeDAO;
import com.chong.bean.CategoryBean;
import com.chong.bean.ResourceBean;
import com.chong.bean.ResourceTypeBean;
import com.chong.common.base.BaseAjaxServlet;
import com.chong.common.base.bean.AjaxJsonBean;
import com.chong.common.util.JacksonUtil;
import com.chong.common.util.StringTools;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class ResourceTypeAjaxServlet extends BaseAjaxServlet
{
    private ResourceTypeDAO resourceTypeDAO = new ResourceTypeDAO();
    
    private ResourceDAO resourceDAO = new ResourceDAO();
    
    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp)
    {
        ResourceTypeBean bean = new ResourceTypeBean();
        
        List<ResourceTypeBean> beans = resourceTypeDAO.queryList(bean);
        
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
    
    public String add(HttpServletRequest req, HttpServletResponse resp)
            throws Exception
    {
        String objJson = (req.getParameter("bean"));
        ResourceTypeBean bean;
        
        if (StringTools.isEmptyOrNone(objJson))
        {
            return "无数据，不能添加";
        }
        
        //获取添加的数据
        bean = (ResourceTypeBean) JacksonUtil.jsonToObj(objJson,
                ResourceTypeBean.class);
        
        //查询是否存在
        ResourceTypeBean typeBeanQ = new ResourceTypeBean();
        typeBeanQ.setName(bean.getName());
        typeBeanQ = resourceTypeDAO.findBean(typeBeanQ);
        if (typeBeanQ != null)
        {
            return "已经存在,添加失败！";
        }
        else
        {
            resourceTypeDAO.addBean(bean);
        }
        
        return null;
        
    }
    
    public boolean update(HttpServletRequest req, HttpServletResponse resp)
            throws Exception
    {
        String objJson = (req.getParameter("bean"));
        ResourceTypeBean bean;
        
        bean = (ResourceTypeBean) JacksonUtil.jsonToObj(objJson,
                ResourceTypeBean.class);
        resourceTypeDAO.updateBean(bean);
        
        return true;
        
    }
    
    public void delete(HttpServletRequest req, HttpServletResponse resp)
    {
        String id = req.getParameter("id");
        
        //判断是否使用了该类型，如果没有的话，则允许删除
        ResourceBean resourceBean = new ResourceBean();
        resourceBean.setType(id);
        List<ResourceBean> resList = resourceDAO.queryList(resourceBean);
        
        if (resList == null || resList.size() == 0)
        {
            ResourceTypeBean resourceTypeBean = new ResourceTypeBean();
            resourceTypeBean.setId(id);
            resourceTypeDAO.deleteBean(resourceTypeBean);
        }
        else
        {
            AjaxJsonBean jsonBean = new AjaxJsonBean();
            jsonBean.setSuccessCode("1");
            jsonBean.setMsg("正在使用，不可以刪除");
            String json;
            try
            {
                json = JacksonUtil.objToJson(jsonBean);
                req.setAttribute("json", json);
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        }
        
    }
    
    public ResourceTypeBean find(HttpServletRequest req, HttpServletResponse resp)
    {
        String id = req.getParameter("id");
        ResourceTypeBean resourceTypeBean = new ResourceTypeBean();
        resourceTypeBean.setId(id);
        resourceTypeBean = resourceTypeDAO.findBean(resourceTypeBean);
        
        return resourceTypeBean;
        
    }
}
