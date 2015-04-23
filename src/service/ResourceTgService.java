package service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DAO.ResourceDAO;
import DAO.ResourceTgDAO;
import DAO.ResourceTypeDAO;
import DAO.TgDAO;
import bean.ResourceBean;
import bean.ResourceTgBean;
import bean.ResourceTypeBean;
import bean.TgBean;

public class ResourceTgService
{
    private ResourceDAO resourceDAO = new ResourceDAO();
    
    private ResourceTypeDAO typeDAO = new ResourceTypeDAO();
    
    private ResourceTgDAO resourceTgDAO = new ResourceTgDAO();
    
    private TgDAO tgDAO = new TgDAO();
    
    public Collection<ResourceTgBean> queryResourceTgList(String tgId,
            ResourceBean resourceBean)
    {
        List<ResourceTypeBean> resourceTypeBeans = typeDAO.queryList(new ResourceTypeBean());
        Map<String, String> rTypeMap = new HashMap<String, String>();
        for (ResourceTypeBean temp : resourceTypeBeans)
        {
            rTypeMap.put(temp.getId(), temp.getName());
        }
        
        TgBean tgBean = new TgBean();        
        tgBean.setId(tgId);
        tgBean =  tgDAO.findBean(tgBean);
        
        
        List<ResourceBean> resourceBeans = resourceDAO.queryList(resourceBean);
        
        Map<String, ResourceTgBean> resourceMap = new HashMap<String, ResourceTgBean>();
        String resourceIds = "";
        for (ResourceBean tempR : resourceBeans)
        {
            resourceIds = resourceIds + tempR.getId() + ",";
            
            ResourceTgBean rtBean = new ResourceTgBean();
            rtBean.setResourceId(tempR.getId());
            rtBean.setResourceDomain(tempR.getDomain());
            rtBean.setRegisterState(tempR.getRegisterState());
            rtBean.setResourceUrl(tempR.getUrl());
            rtBean.setResourceTypeName(rTypeMap.get(tempR.getType()) == null ? tempR.getType()
                    : rTypeMap.get(tempR.getType()));            
            resourceMap.put(tempR.getId(), rtBean);
            if(tgBean!=null){
                
            rtBean.setTgId(tgBean.getId());
            rtBean.setTgDomain(tgBean.getDomain());
            }
        }
        
        ResourceTgBean bean = new ResourceTgBean();
        bean.setTgId(tgId);
        bean.setResourceIds(resourceIds);
        List<ResourceTgBean> beans = resourceTgDAO.queryList(bean);
        
        for (ResourceTgBean tempR : beans)
        {
            ResourceTgBean resourceTgBean = resourceMap.get(tempR.getResourceId());
            if (resourceTgBean == null)
            {
                resourceTgBean = new ResourceTgBean();
            }
            
            tempR.setResourceId(resourceTgBean.getResourceId());
            tempR.setResourceDomain(resourceTgBean.getResourceDomain());
            tempR.setRegisterState(resourceTgBean.getRegisterState());
            
            resourceMap.put(tempR.getResourceId(), tempR);
        }
        
        return resourceMap.values();
    }
    
}
