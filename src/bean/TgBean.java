package bean;

import annotation.SQLAnnotation;

public class TgBean
{
	@SQLAnnotation(update_is_where=true)
    private String id;
	
	@SQLAnnotation
    private String domain;
    
	@SQLAnnotation
    private String remark;



    public String getId() {
		return id;
	}

	public void setId(String id) {
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

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    
    
    
}
