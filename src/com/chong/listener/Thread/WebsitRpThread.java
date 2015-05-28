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
               // resourceBeanQ.setPr("0");
                
                List<ResourceBean> beans = resourceDAO.queryList(resourceBeanQ);
                if (beans == null || beans.size() == 0)
                {
                    Thread.sleep(autoRunConfig.sleeptime);
                }
                else
                {
                    System.out.println("等待查询PR值的个数 ===>" + beans.size());
                    for (ResourceBean tempBean : beans)
                    {
                      if (StringTools.isEmptyOrNone(tempBean.getPr()) ||  Integer.valueOf(tempBean.getPr()) < 1 )
                        {
                            String domain = tempBean.getDomain();
                            domain =domain;
                            int pr = PageRankUtil.getPR(domain);
                            System.out.println(domain + " 的pr值 " + pr);
                            tempBean.setPr(pr + "");
                            resourceDAO.updateBean(tempBean);
                            Thread.sleep(1000*60);
                        }
                        
                    }
                    
                }
                
                Thread.sleep(autoRunConfig.sleeptime);
            }
            catch (Exception e)
            {
                
            }
        }
        
    }
    
}
