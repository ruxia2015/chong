package com.chong.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chong.DAO.CategoryDAO;
import com.chong.DAO.ResourceDAO;
import com.chong.DAO.ResourceTgDAO;
import com.chong.DAO.ResourceTypeDAO;
import com.chong.DAO.TgDAO;
import com.chong.bean.CategoryBean;
import com.chong.bean.ResourceBean;
import com.chong.bean.ResourceTgBean;
import com.chong.bean.ResourceTypeBean;
import com.chong.bean.TgBean;
import com.chong.common.util.StringTools;

public class ResourceTgService
{
    private ResourceDAO resourceDAO = new ResourceDAO();
    
    private ResourceTypeDAO typeDAO = new ResourceTypeDAO();
    
    private ResourceTgDAO resourceTgDAO = new ResourceTgDAO();
    
    private CategoryDAO categoryDAO = new CategoryDAO();
    
    private TgDAO tgDAO = new TgDAO();
    
    public Collection<ResourceTgBean> queryResourceTgList(String tgId,
            ResourceBean resourceBean)
    {
        //查询资源类型
        List<ResourceTypeBean> resourceTypeBeans = typeDAO.queryList(new ResourceTypeBean());
        Map<String, String> rTypeMap = new HashMap<String, String>();
        for (ResourceTypeBean temp : resourceTypeBeans)
        {
            rTypeMap.put(temp.getId(), temp.getName());
        }
        
        //插叙所有的资源类别
        List<CategoryBean> categoryBeans = categoryDAO.queryList(new CategoryBean());
        Map<String, String> categoryMap = new HashMap<String, String>();
        for (CategoryBean temp : categoryBeans)
        {
            categoryMap.put(temp.getId(), temp.getCategory());
        }
        categoryMap.put("-1", "未分类");
        
        TgBean tgBean = new TgBean();
        tgBean.setId(tgId);
        tgBean = tgDAO.findBean(tgBean);
        
        //查询指定类别的资源
        resourceBean.setCategoryIds(tgBean.getCategoryIds());
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
            if (tgBean != null)
            {
                rtBean.setTgId(tgBean.getId());
                rtBean.setTgDomain(tgBean.getDomain());
            }
            rtBean.setCategory(categoryMap.get(tempR.getCategoryId()));
            
         //放入到集合中
            resourceMap.put(tempR.getId(), rtBean);
        }
        
        //查询符合条件的推广的资源的资源账号信息
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
            
            //复制值
            tempR.setResourceId(resourceTgBean.getResourceId());
            tempR.setResourceDomain(resourceTgBean.getResourceDomain());
            tempR.setRegisterState(resourceTgBean.getRegisterState());
            tempR.setCategory(resourceTgBean.getCategory());
            tempR.setResourceTypeName(resourceTgBean.getResourceTypeName());
            tempR.setResourceUrl(resourceTgBean.getResourceUrl());
            
            resourceMap.put(tempR.getResourceId(), tempR);
        }
        
        return resourceMap.values();
    }
    
}
