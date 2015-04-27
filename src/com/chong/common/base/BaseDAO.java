package com.chong.common.base;

import java.sql.SQLException;
import java.sql.Statement;

import com.chong.common.jdbc.JdbcTemplate;

public abstract class BaseDAO<T>
{
    
    public Statement getStatement()
    {
        try
        {
            return JdbcTemplate.getConnection().createStatement();
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    
    public  abstract Class getBeanClass();
    
    
    public abstract String getTableName();
    

}

