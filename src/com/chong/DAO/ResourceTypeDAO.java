package com.chong.DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chong.bean.ResourceTypeBean;
import com.chong.common.base.BaseSimpleDAO;

public class ResourceTypeDAO extends BaseSimpleDAO<ResourceTypeBean>
{

    @Override
    public Class getBeanClass()
    {
        return ResourceTypeBean.class;
    }

    @Override
    public String getTableName()
    {
        // TODO Auto-generated method stub
        return "t_resource_type";
    }
    
    public Map<String,String> queryMap(ResourceTypeBean typeBean){
       List<ResourceTypeBean>  types = queryList(typeBean);
       Map<String,String> typeMap = new HashMap<String, String>();        
        for(ResourceTypeBean temp:types){
            typeMap.put(temp.getId(), temp.getName());
        }
        
        return typeMap;
    }
    
    
    
    
}
