package com.chong.listener.Thread;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.chong.DAO.ResourceDAO;
import com.chong.bean.ResourceBean;
import com.chong.listener.AutoRunConfig;

public class WebsiteConnectThread implements Runnable
{
    
    @Override
    public void run()
    {
        
        ResourceDAO resourceDAO = new ResourceDAO();
        while (AutoRunConfig.queryRp)
        {
            try
            {
                ResourceBean resourceBeanQ = new ResourceBean();
                resourceBeanQ.setAccessState("-1");
                
                List<ResourceBean> beans = resourceDAO.queryList(resourceBeanQ);
                if (beans == null || beans.size() == 0)
                {
                    Thread.sleep(1000 * 60);
                }
                else
                {
                    for (ResourceBean tempBean : beans)
                    {
                        try
                        {
                            String uRL = tempBean.getUrl();
                            HttpURLConnection connection = (HttpURLConnection) new URL(
                                    uRL).openConnection();
                            connection.setRequestProperty("Content-Type",
                                    "application/x-www-form-urlencoded;charset=utf-8");
                            connection.setRequestMethod("GET");
                            connection.connect();
                            tempBean.setAccessState("1");
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            tempBean.setAccessState("2");
                        }
                        finally
                        {
                            resourceDAO.updateBean(tempBean);
                        }
                        
                    }
                    
                }
                
            }
            catch (Exception e)
            {
                
            }
            try
            {
                Thread.sleep(1000 * 60 * 60L);
            }
            catch (InterruptedException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
    }
    
    public static void main(String[] args)
    {
        try
        {
            String uRL = "http://www.ppurl.com/kf/201305/208770.html";
            HttpURLConnection connection = (HttpURLConnection) new URL(uRL).openConnection();
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded;charset=utf-8");
            connection.setRequestMethod("GET");
            connection.connect();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
