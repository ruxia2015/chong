package servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.ResourceDAO;
import DAO.ResourceTypeDAO;
import base.BaseServlet;
import bean.ResourceBean;
import bean.ResourceTypeBean;

import common.StringTools;

public class ResourceServlet extends BaseServlet
{
    private ResourceTypeDAO resourceTypeDAO = new ResourceTypeDAO();
    
    private ResourceDAO resourceDAO = new ResourceDAO();
    
    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp)
            throws Exception
    {
        ResourceBean bean = new ResourceBean();
        
        List<ResourceBean> beans = resourceDAO.queryList(bean);
        
        req.setAttribute("resourceList", beans);
        
        req.getRequestDispatcher("/queryResource.jsp").forward(req, resp);
        
    }
    
    public void toAdd(HttpServletRequest req, HttpServletResponse resp)
            throws Exception
    {
        List<ResourceTypeBean> resourceTypeBeans = resourceTypeDAO.queryList(new ResourceTypeBean());
        req.setAttribute("resourceTypes", resourceTypeBeans);
        req.getRequestDispatcher("/addResource.jsp").forward(req, resp);
        
    }
    
    public void add(HttpServletRequest req, HttpServletResponse resp)
            throws Exception
    {
        String urls = req.getParameter("urls");
        String resourceType = req.getParameter("resourceType");
        String override = req.getParameter("override");
        
        String message = "";
        int addCnt = 0;
        
       if( !StringTools.isEmptyOrNone(urls)){
           String[] urlArr = urls.split("\n");
           for (String str : urlArr)
           {
               if (StringTools.isEmptyOrNone(str))
               {
                   continue;
               }
               String domain = getDomain(str);
               if (StringTools.isEmptyOrNone(domain))
               {
                   continue;
               }
               
               //是否存在
               ResourceBean resourceBean = new ResourceBean();
               resourceBean.setDomain(domain);
               resourceBean = resourceDAO.findBean(resourceBean);
               
               //添加资源
               if (resourceBean == null)
               {
                   resourceBean = new ResourceBean();
                   resourceBean.setType(resourceType);
                   resourceBean.setDomain(domain);
                   resourceBean.setUrl(str);
                   resourceDAO.addBean(resourceBean);
                   addCnt++;
               }
               else if ("on".equals(override)
                       && !StringTools.isEmptyOrNone(resourceType))
               {
                   //更新资源类型
                   resourceBean.setType(resourceType);
                   resourceDAO.updateBean(resourceBean);
               }
           }
           message = "添加成功的个数为 【" + addCnt +"】";
           
       }else{
           message = "地址不能为空";
       }
       
       req.setAttribute("message", message);
//       req.getRequestDispatcher("/addResource.jsp").forward(req, resp);       
       toAdd(req, resp);
        
        
    }
    
    public static String getDomain(String url)
    {
        if (StringTools.isEmptyOrNone(url))
        {
            return null;
        }
        if (!url.contains("."))
        {
            return null;
        }
        
        String domain = url.replaceFirst("^http[s]?://+", "");
        
        if (domain.indexOf("/") > 0)
        {
            domain = domain.substring(0, domain.indexOf("/"));
        }
        if (domain.indexOf("?") > 0)
        {
            domain = domain.substring(0, domain.indexOf("?"));
        }
        
        String[] arr = domain.split("\\.");
        
        if (arr.length > 2)
        {
            domain = arr[arr.length - 2] + "." + arr[arr.length - 1];
        }
        
        System.out.println(domain);
        return domain;
        
    }
    
    public static void main(String[] args)
    {
        getDomain("https://www.qq.baidu.com/?wwwww");
    }
    
}
