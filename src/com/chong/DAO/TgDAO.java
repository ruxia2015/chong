package com.chong.DAO;

import com.chong.bean.TgBean;
import com.chong.common.base.BaseSimpleDAO;

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
