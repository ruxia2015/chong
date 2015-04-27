package com.chong.common.base.bean;

import java.util.List;

public class AjaxJsonBean
{
    private String successCode;
    
    private List json;
    
    private String msg;
    
    
    
    
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
