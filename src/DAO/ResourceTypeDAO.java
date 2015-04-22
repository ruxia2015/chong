package DAO;

import base.BaseSimpleDAO;
import bean.ResourceTypeBean;

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
