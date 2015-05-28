package com.chong.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chong.DAO.CategoryDAO;
import com.chong.DAO.TgDAO;
import com.chong.bean.CategoryBean;
import com.chong.bean.TgBean;
import com.chong.common.base.BaseServlet;
import com.chong.common.util.StringTools;

public class TgServlet extends BaseServlet
{
    private TgDAO tgDAO = new TgDAO();
    
    private CategoryDAO categoryDAO = new CategoryDAO();
    
    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp)
    {
        //        TgBean bean = new TgBean();
        //        
        //        List<TgBean> beans = tgDAO.queryList(bean);
        //        
        //        req.setAttribute("tgList", beans);
        try
        {
            //            req.getRequestDispatcher("/queryTg.jsp").forward(req, resp);
            
            req.setAttribute("userJs", "tg.js");
            req.getRequestDispatcher("/queryList.jsp").forward(req, resp);
        }
        catch (ServletException e)
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
    
    protected void toAdd(HttpServletRequest req, HttpServletResponse resp)
            throws Exception
    {
        
        CategoryBean categoryBean = new CategoryBean();
        List<CategoryBean> categoryBeans = categoryDAO.queryList(categoryBean);
        req.setAttribute("categoryList", categoryBeans);
        
        req.getRequestDispatcher("/addTg.jsp").forward(req, resp);
        
    }
    
    protected void add(HttpServletRequest req, HttpServletResponse resp)
            throws Exception
    {
        String categoryIds = req.getParameter("categoryIds");
        String tgs = req.getParameter("tgs");
        
        if (!StringTools.isEmptyOrNone(tgs))
        {
            String[] tgArr = tgs.split("\n");
            TgBean tgBean = new TgBean();
            for (String str : tgArr)
            {
                str = str.trim();
                tgBean.setDomain(str);
                tgBean.setCategoryIds(categoryIds);
                
                TgBean bean = new TgBean();
                bean.setDomain(str);
                bean = tgDAO.findBean(bean);
                
                if(bean==null){
                    tgDAO.addBean(tgBean);                    
                }
            }
            
        }
        
        String message = "添加成功";
        req.setAttribute("message", message);
        toAdd(req, resp);
        
    }
    
}
