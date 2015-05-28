package com.chong.listener.Thread;

import java.util.List;

import com.chong.DAO.ResourceDAO;
import com.chong.bean.ResourceBean;
import com.chong.common.util.PageRankUtil;
import com.chong.common.util.StringTools;
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
                ResourceBean resourceBeanQ = new ResourceBean();
                resourceBeanQ.setPr("-1");
                
                List<ResourceBean> beans = resourceDAO.queryList(resourceBeanQ);
                if (beans == null || beans.size() == 0)
                {
                    Thread.sleep(1000 * 60);
                }
                else
                {
                    for (ResourceBean tempBean : beans)
                    {
                        if (StringTools.isEmptyOrNone(tempBean.getPr())
                                || tempBean.getPr().equals("0"))
                        {
                            String domain = tempBean.getDomain();
                            int pr = PageRankUtil.getPR(domain);
                            System.out.println(domain + "的pr值" + pr);
                            tempBean.setPr(pr + "");
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
    
}
