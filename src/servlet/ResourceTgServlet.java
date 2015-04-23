package servlet;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.ResourceTgService;
import DAO.ResourceTgDAO;
import base.BaseServlet;
import bean.ResourceBean;
import bean.ResourceTgBean;
import common.CsvUtil;

public class ResourceTgServlet extends BaseServlet
{
    private ResourceTgDAO tgDAO = new ResourceTgDAO();
    
    private ResourceTgService resourceTgService = new ResourceTgService();
    
    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp)
    {
        
        //    	req.getParameter("tgId");    	
        //        ResourceTgBean bean = new ResourceTgBean();
        
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
    
    protected void export(HttpServletRequest req, HttpServletResponse resp)
    {
        String tgId = req.getParameter("tgId");
        ResourceTgBean bean = new ResourceTgBean();
        bean.setTgId(tgId);
        tgId = "1";
        Collection<ResourceTgBean> beans = resourceTgService.queryResourceTgList(tgId,
                new ResourceBean());
        
        resp.setContentType("text/plain");
        String fileName = null;
        try
        {
            fileName = URLEncoder.encode("推广资源导出", "UTF-8");
        }
        catch (UnsupportedEncodingException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        resp.setHeader("Content-Disposition", "attachment; filename="
                + fileName + ".csv");
        
        BufferedOutputStream buff = null;
        String content = CsvUtil.getCsvContent(beans);
        ServletOutputStream outSTr = null;
        try
        {
            outSTr = resp.getOutputStream(); // 建立       
            buff = new BufferedOutputStream(outSTr);
            
            buff.write(content.toString().getBytes());
            buff.flush();
            buff.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                buff.close();
                outSTr.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        
    }
}
