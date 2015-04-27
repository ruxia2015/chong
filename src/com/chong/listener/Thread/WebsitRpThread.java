package com.chong.listener.Thread;

import java.util.List;

import com.chong.DAO.ResourceDAO;
import com.chong.bean.ResourceBean;
import com.chong.listener.AutoRunConfig;

public class WebsitRpThread implements Runnable
{
    AutoRunConfig autoRunConfig;
    
    
    
    @Override
    public void run()
    {
        ResourceDAO resourceDAO = new ResourceDAO();
        while (AutoRunConfig.queryRp)
        {
            try
            {
             List<ResourceBean> beans= resourceDAO.queryList(new ResourceBean());
             for(ResourceBean tempBean:beans){
                 String url = tempBean.getUrl();
                 
                 
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
    
}
