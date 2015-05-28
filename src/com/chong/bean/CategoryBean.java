package com.chong.bean;

import com.chong.common.annotation.SQLAnnotation;

public class CategoryBean extends PageBean
{
 
    @SQLAnnotation(update_is_where = true)
    private String id;
    
    /**
     * 资源分类
     */
    @SQLAnnotation
    private String category;
    
    /**
     * 备注
     */
    private String remark;


    public String getId()
    {
        return id;
    }


    public void setId(String id)
    {
        this.id = id;
    }


    public String getCategory()
    {
        return category;
    }


    public void setCategory(String category)
    {
        this.category = category;
    }


    public String getRemark()
    {
        return remark;
    }


    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    
    
    
    
}
