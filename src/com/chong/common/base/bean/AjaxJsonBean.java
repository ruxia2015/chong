package com.chong.common.base.bean;

import java.util.List;

public class AjaxJsonBean
{
    private String successCode;
    
    private List json;
    
    private String msg;
    
    private Object bean;
    
    
    
    
    
    
    public Object getBean()
    {
        return bean;
    }

    public void setBean(Object bean)
    {
        this.bean = bean;
    }

    public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSuccessCode()
    {
        return successCode;
    }
    
    public void setSuccessCode(String successCode)
    {
        this.successCode = successCode;
    }

	public List getJson() {
		return json;
	}

	public void setJson(List json) {
		this.json = json;
	}
    

    
}
