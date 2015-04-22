package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.ResourceDAO;
import DAO.ResourceTgDAO;
import base.BaseServlet;
import bean.ResourceBean;
import bean.ResourceTgBean;

public class ResourceTgServlet extends BaseServlet
{
    private ResourceTgDAO tgDAO = new ResourceTgDAO();

    

    
    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp)
    {
    	
    	
    	System.out.println(
    			req.getParameter("tgId")
    			);
    	
        ResourceTgBean bean = new ResourceTgBean();
        
        
//        List<ResourceTgBean> beans = tgDAO.queryList(bean);
        
//        req.setAttribute("tgList", beans);
        try
        {
            req.getRequestDispatcher("/queryResourceTg.jsp").forward(req, resp);
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
