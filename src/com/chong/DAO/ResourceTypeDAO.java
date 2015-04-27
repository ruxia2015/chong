package com.chong.DAO;

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
    
    
    
    
}
