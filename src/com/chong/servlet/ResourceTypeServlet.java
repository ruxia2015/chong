package com.chong.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chong.DAO.ResourceTypeDAO;
import com.chong.bean.ResourceTypeBean;
import com.chong.common.base.BaseServlet;

public class ResourceTypeServlet extends BaseServlet
{
private ResourceTypeDAO resourceTypeDAO = new ResourceTypeDAO();



@Override
protected void execute(HttpServletRequest req, HttpServletResponse resp)
{
    ResourceTypeBean bean = new ResourceTypeBean();
    
    List<ResourceTypeBean> beans = resourceTypeDAO.queryList(bean);
    
    req.setAttribute("resourceTypeList", beans);
    try
    {
        req.getRequestDispatcher("/queryResourceType.jsp").forward(req, resp);
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

