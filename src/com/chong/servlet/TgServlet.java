package com.chong.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chong.DAO.TgDAO;
import com.chong.bean.TgBean;
import com.chong.common.base.BaseServlet;

public class TgServlet extends BaseServlet
{
    private TgDAO tgDAO = new TgDAO();
    

    
    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp)
    {
        TgBean bean = new TgBean();
        
        List<TgBean> beans = tgDAO.queryList(bean);
        
        req.setAttribute("tgList", beans);
        try
        {
            req.getRequestDispatcher("/queryTg.jsp").forward(req, resp);
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
    
}
