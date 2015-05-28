package com.chong.DAO;

import com.chong.bean.CategoryBean;
import com.chong.bean.ResourceBean;
import com.chong.common.base.BasePagingDAO;

public class CategoryDAO extends BasePagingDAO<CategoryBean>
{
    @Override
    public Class getBeanClass()
    {
        return CategoryBean.class;
    }
    
    @Override
    public String getTableName()
    {
        // TODO Auto-generated method stub
        return "t_category";
    }
    
}
