package jdbc;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import content.PropertyUtil;

public class JdbcTemplate
{

    
    
    public static Connection getConnection(){
        Connection con = null;
        PropertyUtil propertyUtil = new PropertyUtil(
                PropertyUtil.class.getResource("/jdbcConfig.properties").getPath());
        
        StringBuffer sb = new StringBuffer();
        sb.append(propertyUtil.getValue("database.url"));        
        
        try
        {
            String Driver=propertyUtil.getValue("database.driver");
            Class.forName(Driver);
            con = DriverManager.getConnection(sb.toString(),propertyUtil.getValue("database.user"),propertyUtil.getValue("database.password"));
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return con;
    }
    
    
    
   public static void main(String[] args)
{
       System.out.println(JdbcTemplate.getConnection());
    
}
    
    

}
