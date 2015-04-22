package base;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import common.ResultSetHandler;
import common.SimpleSQLTemplate;
import common.StringTools;

public abstract class BaseSimpleDAO<T> extends BaseDAO<T>
{
    private SimpleSQLTemplate<T> generatorSimpleSQLTemplate = new SimpleSQLTemplate<T>();
    
    public List<T> queryList(T bean)
    {
      String sql =  generatorSimpleSQLTemplate.generatorQuerySQL(getTableName(), bean, getBeanClass());
      
        try
        {
            if(!StringTools.isEmptyOrNone(sql)){
                
            ResultSet resultSet = getStatement().executeQuery(sql);
            return ResultSetHandler.resultSetToList(resultSet, getBeanClass());
            }
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
        
    }
    
    public T findBean(T t)
    {
        String sql =  generatorSimpleSQLTemplate.generatorQuerySQL(getTableName(), t, getBeanClass());
        
        return null;
    }
    
    public int updateBean(T t)
    {
        String sql =  generatorSimpleSQLTemplate.generatorUpdateSQL(getTableName(), t, getBeanClass());
        try
        {
            return  getStatement().executeUpdate(sql);
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
        String sql =  generatorSimpleSQLTemplate.generatorAddSQL(getTableName(), t, getBeanClass());
        
        try
        {
            return  getStatement().executeUpdate(sql);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return 0;
    }
    
    public int deleteBean(T t)
    {
        String sql =  generatorSimpleSQLTemplate.generatorDeleteSQL(getTableName(), t, getBeanClass());
        try
        {
            return  getStatement().executeUpdate(sql);
        }
        catch (SQLException e)
        {
          
            e.printStackTrace();
        }
        return 0;
    }
    
}
