package DAO;

import base.BaseSimpleDAO;
import bean.ResourceBean;
import bean.ResourceTypeBean;

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
