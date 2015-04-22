package DAO;

import base.BaseDAO;
import base.BaseSimpleDAO;
import bean.ResourceTypeBean;
import bean.TgBean;

public class TgDAO extends BaseSimpleDAO<TgBean>
{

    @Override
    public Class getBeanClass()
    {
        return TgBean.class;
    }

    @Override
    public String getTableName()
    {
        // TODO Auto-generated method stub
        return "t_tgwebsite";
    }
    
    
    
    
}
