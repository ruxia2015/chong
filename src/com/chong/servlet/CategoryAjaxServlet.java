package com.chong.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chong.DAO.CategoryDAO;
import com.chong.bean.CategoryBean;
import com.chong.common.base.BaseAjaxServlet;
import com.chong.common.util.JacksonUtil;
import com.chong.common.util.StringTools;
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
    
    public String add(HttpServletRequest req, HttpServletResponse resp)
            throws Exception
    {
        String objJson = (req.getParameter("bean"));
        if (StringTools.isEmptyOrNone(objJson))
        {
            return "无数据，不能添加";
        }
        
        CategoryBean categoryBean = (CategoryBean) JacksonUtil.jsonToObj(objJson,
                CategoryBean.class);
        
        //判断是否可以添加
        CategoryBean beanQ = new CategoryBean();
        beanQ.setCategory(categoryBean.getCategory());
        beanQ = categoryDAO.findBean(beanQ);
        if (beanQ != null)
        {
            return "已经存在该分类，添加失败";
        }
        
        //添加        
        categoryDAO.addBean(categoryBean);
        
        return null;
        
    }
    
    public void update(HttpServletRequest req, HttpServletResponse resp)
            throws Exception
    {
        String objJson = (req.getParameter("bean"));
        System.out.println("=========" + objJson);
        
        CategoryBean categoryBean = (CategoryBean) JacksonUtil.jsonToObj(objJson,
                CategoryBean.class);
        categoryDAO.updateBean(categoryBean);
        
    }
    
    public void delete(HttpServletRequest req, HttpServletResponse resp)
    {
        String id = req.getParameter("id");
        
        CategoryBean categoryBean = new CategoryBean();
        categoryBean.setId(id);
        categoryDAO.deleteBean(categoryBean);
    }
    
}
