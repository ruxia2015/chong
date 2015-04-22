package bean;

public class ResourceAccount
{
    private int id;
    
    private int resourceId;
    
    private int tgId;
    
    private String username;
    
    private String password;
    
    private String email;
    
    private String state;
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public int getResourceId()
    {
        return resourceId;
    }
    
    public void setResourceId(int resourceId)
    {
        this.resourceId = resourceId;
    }
    
    public int getTgId()
    {
        return tgId;
    }
    
    public void setTgId(int tgId)
    {
        this.tgId = tgId;
    }
    
    public String getUsername()
    {
        return username;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
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
    
    public String getState()
    {
        return state;
    }
    
    public void setState(String state)
    {
        this.state = state;
    }
    
}
