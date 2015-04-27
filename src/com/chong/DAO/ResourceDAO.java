package com.chong.DAO;

import com.chong.bean.ResourceBean;
import com.chong.common.base.BasePagingDAO;

public class ResourceDAO extends BasePagingDAO<ResourceBean>
{

    @Override
    public Class getBeanClass()
    {
        return ResourceBean.class;
    }

    @Override
    public String getTableName()
    {
        // TODO Auto-generated method stub
        return "t_resource";
    }
    
    
    
    
}
