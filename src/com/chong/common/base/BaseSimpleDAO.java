package com.chong.common.base;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.chong.common.util.ResultSetHandler;
import com.chong.common.util.SimpleSQLTemplate;
import com.chong.common.util.StringTools;

public abstract class BaseSimpleDAO<T> extends BaseDAO<T>
{
    private SimpleSQLTemplate<T> generatorSimpleSQLTemplate = new SimpleSQLTemplate<T>();
    
    public int count(T bean)
    {
        
        String sql = generatorSimpleSQLTemplate.generatorCountSQL(getTableName(),
                bean,
                getBeanClass());
        
        Integer cnt = null;
        
        try
        {
            if (!StringTools.isEmptyOrNone(sql))
            {
                
                ResultSet resultSet = getStatement().executeQuery(sql);
                
                Object obj = resultSet.getObject(1);
                if (obj == null)
                {
                    return 0;
                }
                
                cnt = Integer.parseInt(obj.toString());
                
            }
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return cnt == null ? 0 : cnt;
        
    }
    
    public List<T> queryList(T bean)
    {
        
        String sql = generatorSimpleSQLTemplate.generatorQuerySQL(getTableName(),
                bean,
                getBeanClass());
        
        try
        {
            if (!StringTools.isEmptyOrNone(sql))
            {
                
                ResultSet resultSet = getStatement().executeQuery(sql);
                return ResultSetHandler.resultSetToList(resultSet,
                        getBeanClass());
            }
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return null;
        
    }
    
    public T findBean(T t)
    {
        String sql = generatorSimpleSQLTemplate.generatorQuerySQL(getTableName(),
                t,
                getBeanClass());
        
        List<T> ts = queryList(t);
        if (ts != null && ts.size() > 0)
        {
            return ts.get(0);
        }
        
        return null;
    }
    
    public int updateBean(T t)
    {
        String sql = generatorSimpleSQLTemplate.generatorUpdateSQL(getTableName(),
                t,
                getBeanClass());
        try
        {
            return getStatement().executeUpdate(sql);
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }
    
    public int addBean(T t)
    {
        String sql = generatorSimpleSQLTemplate.generatorAddSQL(getTableName(),
                t,
                getBeanClass());
        
        try
        {
            return getStatement().executeUpdate(sql);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return 0;
    }
    
    public int deleteBean(T t)
    {
        String sql = generatorSimpleSQLTemplate.generatorDeleteSQL(getTableName(),
                t,
                getBeanClass());
        try
        {
            return getStatement().executeUpdate(sql);
        }
        catch (SQLException e)
        {
            
            e.printStackTrace();
        }
        return 0;
    }
    
}
