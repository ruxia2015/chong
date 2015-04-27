package com.chong.servlet;

import java.io.BufferedOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chong.DAO.ResourceDAO;
import com.chong.DAO.ResourceTypeDAO;
import com.chong.bean.ResourceBean;
import com.chong.bean.ResourceTypeBean;
import com.chong.common.base.BaseServlet;
import com.chong.common.util.CsvUtil;
import com.chong.common.util.DateUtil;
import com.chong.common.util.StringTools;

public class ResourceServlet extends BaseServlet
{
    private ResourceTypeDAO resourceTypeDAO = new ResourceTypeDAO();
    
    private ResourceDAO resourceDAO = new ResourceDAO();
    
    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp)
            throws Exception
    {
        ResourceTypeBean bean = new ResourceTypeBean();
        
        List<ResourceTypeBean> beans = resourceTypeDAO.queryList(bean);
        
        req.setAttribute("resourceTypeList", beans);
        
        req.getRequestDispatcher("/queryResource.jsp").forward(req, resp);
        
    }
    
    protected void export(HttpServletRequest req, HttpServletResponse resp)
    {
        Map<String,String> typeMap = resourceTypeDAO.queryMap(new ResourceTypeBean());        
        
        
        String domain = (String)req.getParameter("domain");
        String type = (String)req.getParameter("type");
        ResourceBean bean = new ResourceBean();
        
        bean.setDomain(domain);
        bean.setType(type);
        List<ResourceBean> beans = resourceDAO.queryList(bean);
        
        for(int i=0;i<beans.size();i++){
            ResourceBean tempBean = beans.get(i);
            tempBean.setType(typeMap.get(tempBean.getType()));
            beans.set(i, tempBean);
        }
        
        resp.setContentType("text/plain");
        String fileName = null;
        try
        {
            fileName = URLEncoder.encode("资源导出", "UTF-8");
            
            fileName = fileName +"_" +DateUtil.now();
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
               str = str.trim();
               
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
