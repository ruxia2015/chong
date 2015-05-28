package com.chong.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.chong.common.util.PropertyUtil;
import com.chong.listener.Thread.WebsitRpThread;
import com.chong.listener.Thread.WebsiteConnectThread;

public class AutoRunListener implements ServletContextListener
{

    @Override
    public void contextDestroyed(ServletContextEvent arg0)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0)
    {
        System.out.println("启动监听器=============");
        PropertyUtil propertyUtil = new PropertyUtil(
                PropertyUtil.class.getResource("/config.properties").getPath());
        
        Boolean checkCon = Boolean.parseBoolean(propertyUtil.getValue("auto.checkConnection"));
        Boolean checkRp =  Boolean.parseBoolean(propertyUtil.getValue("auto.queryRp"));
        
        if(checkCon==null){
            checkCon = true;
        }        
        AutoRunConfig.checkConnection = checkCon;   
        
        
        if(checkRp==null){
            checkRp = true;
        }  
        AutoRunConfig.queryRp = checkRp;
        
        //自动查询网网站
        WebsiteConnectThread connectThread = new WebsiteConnectThread();
        new Thread(connectThread).start();
        
        //自动查询网站rp
        WebsitRpThread rpThread = new WebsitRpThread();
        new Thread(rpThread).start();
        
    }
    
    
    
}
