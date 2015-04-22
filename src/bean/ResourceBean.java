package bean;

public class ResourceBean
{
    /**
     * id
     */
    private String id;
    
    /**
     * 域名
     */
    private String domain;
    
    /**
     * 注册类型
     */
    private String url;    
    
    /**
     * 资源类型  01 blog 
     */
    private String type;
    
    /**
     * 可以访问状态
     * -1 等待检测   1 可以访问  2  不可以访问
     */
    private String accessState;
    
    /**
     * 是否可以注册
     * -1 等待    1 可以注册  2 不可以注册
     */
    private String registerState;
    
    /**
     * 其他状态
     */
    private String otherState;
    
    
    /**
     * 备注
     */
    private String remark;
       
    
    
    
    
    
    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getAccessState()
    {
        return accessState;
    }

    public void setAccessState(String accessState)
    {
        this.accessState = accessState;
    }

    public String getRegisterState()
    {
        return registerState;
    }

    public void setRegisterState(String registerState)
    {
        this.registerState = registerState;
    }

    public String getOtherState()
    {
        return otherState;
    }

    public void setOtherState(String otherState)
    {
        this.otherState = otherState;
    }

    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getDomain()
    {
        return domain;
    }
    
    public void setDomain(String domain)
    {
        this.domain = domain;
    }
    
    public String getUrl()
    {
        return url;
    }
    
    public void setUrl(String url)
    {
        this.url = url;
    }
    
}
