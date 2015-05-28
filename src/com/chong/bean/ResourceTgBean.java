package com.chong.bean;

import com.chong.common.annotation.ExportHeaderAnnotation;
import com.chong.common.annotation.SQLAnnotation;

public class ResourceTgBean
{
    /**
     * id
     */
    
    @SQLAnnotation(update_is_where = true)
    private String id;
    
    private String tgId;
    
    @ExportHeaderAnnotation(headerName = "资源ID")
    private String resourceId;
    
    @ExportHeaderAnnotation(headerName = "账号")
    private String account;
    
    @ExportHeaderAnnotation(headerName = "密码")
    private String password;
    
    @ExportHeaderAnnotation(headerName = "邮箱")
    private String email;
    
    @ExportHeaderAnnotation(headerName = "推广的站")
    @SQLAnnotation(is_Ingore = true)
    private String tgDomain;
    
    @SQLAnnotation(is_column = false, where_oper = "in", where_column = "resourceId")
    private String resourceIds;
    
    @ExportHeaderAnnotation(headerName = "资源域名")
    @SQLAnnotation(is_Ingore = true)
    private String resourceDomain;
    
    @ExportHeaderAnnotation(isIngore = true)
    @SQLAnnotation(is_Ingore = true)
    private String registerState;
    
    @ExportHeaderAnnotation(headerName = "资源类型")
    @SQLAnnotation(is_Ingore = true)
    private String resourceTypeName;
    
    @ExportHeaderAnnotation(headerName = "资源")
    @SQLAnnotation(is_Ingore = true)
    private String resourceUrl;
    
    @ExportHeaderAnnotation(headerName = "资源分类")
    @SQLAnnotation(is_Ingore = true)
    private String category;
    
    
    
    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getResourceUrl()
    {
        return resourceUrl;
    }
    
    public void setResourceUrl(String resourceUrl)
    {
        this.resourceUrl = resourceUrl;
    }
    
    public String getResourceTypeName()
    {
        return resourceTypeName;
    }
    
    public void setResourceTypeName(String resourceTypeName)
    {
        this.resourceTypeName = resourceTypeName;
    }
    
    public String getResourceIds()
    {
        return resourceIds;
    }
    
    public void setResourceIds(String resourceIds)
    {
        this.resourceIds = resourceIds;
    }
    
    public String getResourceDomain()
    {
        return resourceDomain;
    }
    
    public void setResourceDomain(String resourceDomain)
    {
        this.resourceDomain = resourceDomain;
    }
    
    public String getRegisterState()
    {
        return registerState;
    }
    
    public void setRegisterState(String registerState)
    {
        this.registerState = registerState;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getTgId()
    {
        return tgId;
    }
    
    public void setTgId(String tgId)
    {
        this.tgId = tgId;
    }
    
    public String getResourceId()
    {
        return resourceId;
    }
    
    public void setResourceId(String resourceId)
    {
        this.resourceId = resourceId;
    }
    
    public String getAccount()
    {
        return account;
    }
    
    public void setAccount(String account)
    {
        this.account = account;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    public String getEmail()
    {
        return email;
    }
    
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    public String getTgDomain()
    {
        return tgDomain;
    }
    
    public void setTgDomain(String tgDomain)
    {
        this.tgDomain = tgDomain;
    }
    
}
