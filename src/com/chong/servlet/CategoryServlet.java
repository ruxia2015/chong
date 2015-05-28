package com.chong.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chong.DAO.CategoryDAO;
import com.chong.bean.CategoryBean;
import com.chong.common.base.BaseServlet;

public class CategoryServlet extends BaseServlet
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    private CategoryDAO categoryDAO = new CategoryDAO();
    
    
    protected void execute(HttpServletRequest req, HttpServletResponse resp)
            throws Exception
    {
//        CategoryBean bean = new CategoryBean();
//        
//        List<CategoryBean> beans = categoryDAO.queryList(bean);
//        
//        req.setAttribute("categoryList", beans);
        req.setAttribute("userJs", "category.js");        
        req.getRequestDispatcher("/queryList.jsp").forward(req, resp);
        
    }
}
