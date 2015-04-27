package com.chong.DAO;

import com.chong.bean.ResourceTgBean;
import com.chong.common.base.BaseSimpleDAO;

public class ResourceTgDAO extends BaseSimpleDAO<ResourceTgBean>
{

    @Override
    public Class getBeanClass()
    {
        return ResourceTgBean.class;
    }

    @Override
    public String getTableName()
    {
        // TODO Auto-generated method stub
        return "t_resource_tg";
    }
    
    
    
    
}
