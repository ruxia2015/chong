package DAO;

import base.BaseSimpleDAO;
import bean.ResourceBean;
import bean.ResourceTgBean;
import bean.ResourceTypeBean;

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
