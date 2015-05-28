package com.chong.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chong.DAO.CategoryDAO;
import com.chong.bean.CategoryBean;
import com.chong.common.base.BaseAjaxServlet;
import com.chong.common.util.JacksonUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class CategoryAjaxServlet extends BaseAjaxServlet
{
    private CategoryDAO categoryDAO = new CategoryDAO();
    
    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp)
    {
        
        String category = (String) req.getParameter("category");
        String remark = (String) req.getParameter("remark");
        
        CategoryBean bean = new CategoryBean();
        
        bean.setCategory(category);
        bean.setRemark(remark);
        List<CategoryBean> beans = categoryDAO.queryList(bean);
        
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
    {
        String objJson = (req.getParameter("bean"));
        CategoryBean categoryBean;
        try
        {
            categoryBean = (CategoryBean) JacksonUtil.jsonToObj(objJson,
                    CategoryBean.class);
            categoryDAO.addBean(categoryBean);
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
        String objJson = (req.getParameter("bean"));
        System.out.println("=========" + objJson);
        
        CategoryBean categoryBean;
        try
        {
            categoryBean = (CategoryBean) JacksonUtil.jsonToObj(objJson,
                    CategoryBean.class);
            categoryDAO.updateBean(categoryBean);
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
    
    public void delete(HttpServletRequest req, HttpServletResponse resp)
    {
        String id = req.getParameter("id");
        
        CategoryBean categoryBean = new CategoryBean();
        categoryBean.setId(id);
        categoryDAO.deleteBean(categoryBean);
    }
    
}
