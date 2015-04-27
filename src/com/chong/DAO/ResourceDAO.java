package com.chong.DAO;

import com.chong.bean.ResourceBean;
import com.chong.common.base.BaseSimpleDAO;

public class ResourceDAO extends BaseSimpleDAO<ResourceBean>
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
