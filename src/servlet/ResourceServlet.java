package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.ResourceDAO;
import base.BaseServlet;
import bean.ResourceBean;

public class ResourceServlet extends BaseServlet
{
    private ResourceDAO resourceDAO = new ResourceDAO();
    
    @Override
    protected Object getObject()
    {
        return new ResourceServlet();
    }
    
    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp)
    {
        ResourceBean bean = new ResourceBean();
        
        List<ResourceBean> beans = resourceDAO.queryList(bean);
        
        req.setAttribute("resourceList", beans);
        try
        {
            req.getRequestDispatcher("/queryResource.jsp").forward(req, resp);
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
