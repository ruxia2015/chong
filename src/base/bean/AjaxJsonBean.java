package base.bean;

public class AjaxJsonBean
{
    private String successCode;
    
    private Object json;
    
    public String getSuccessCode()
    {
        return successCode;
    }
    
    public void setSuccessCode(String successCode)
    {
        this.successCode = successCode;
    }
    
    public Object getJson()
    {
        return json;
    }
    
    public void setJson(Object json)
    {
        this.json = json;
    }
    
}
