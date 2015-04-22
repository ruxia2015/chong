package bean;

import annotation.SQLAnnotation;

public class ResourceTypeBean
{
    
	@SQLAnnotation(update_is_where=true)
    private String id;
    
	@SQLAnnotation(update_is_where=false)
    private String name;
    
    private String remark;
    
    
    
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
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
