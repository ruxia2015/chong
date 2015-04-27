package com.chong.common.base;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.chong.bean.PageBean;
import com.chong.common.util.ResultSetHandler;
import com.chong.common.util.SimpleSQLTemplate;
import com.chong.common.util.StringTools;

public abstract class BasePagingDAO<T extends PageBean> extends
        BaseSimpleDAO<T>
{
    private SimpleSQLTemplate<T> generatorSimpleSQLTemplate = new SimpleSQLTemplate<T>();
    
    public List<T> queryPagingList(T bean)
    {
        
        int count = count(bean);
        bean.setTotalSize(count);
        
        String sql = generatorSimpleSQLTemplate.generatorQuerySQL(getTableName(),
                bean,
                getBeanClass());
        
        sql = sql + " limit " + bean.getStartLimit() + "," + bean.getEndLimit();
        
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
    

}
