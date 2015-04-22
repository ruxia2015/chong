package common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import annotation.SQLAnnotation;

public class SimpleSQLTemplate<T>
{
    
    public String generatorAddSQL(String tableName, T t, Class clazz)
    {
        
        Map<String, Object> map = getObjectValue(t, clazz);
        
        StringBuffer sb = new StringBuffer();
        String columns = "";
        String values = "";
        for (String key : map.keySet())
        {
            String val = map.get(key).toString();
            if (!StringTools.isEmptyOrNone(val))
            {
                columns = columns + key + ",";
                values = values + "'" + val + "'" + ",";
            }
        }
        
        if (StringTools.isEmptyOrNone(columns))
        {
            return null;
        }
        else
        {
            sb.append("insert into " + tableName);
            sb.append(" (" + columns + " )");
            sb.append(" values(" + values + ")");
        }
        
        return sb.toString();
    }
    
    public String generatorDeleteSQL(String tableName, T t, Class clazz)
    {
        Map<String, Object> map = getObjectValue(t, clazz);
        StringBuffer sb = new StringBuffer();
        String where = "";
        for (String key : map.keySet())
        {
            String val = map.get(key).toString();
            if (!StringTools.isEmptyOrNone(val))
            {
                val = "'+val+'";
                where = where + " AND " + key + " = " + val;
            }
        }
        
        if (StringTools.isEmptyOrNone(where))
        {
            return null;
        }
        else
        {
            sb.append("delete  " + tableName);
            sb.append(" WHERE 1 = 1 " + where);
        }
        return sb.toString();
        
    }
    
    public String generatorQuerySQL(String tableName, T t, Class clazz)
    {
        Map<String, Object> map = getObjectValue(t, clazz);
        StringBuffer sb = new StringBuffer();
        String where = "";
        String columns = "";
        for (String key : map.keySet())
        {
            columns = columns + key + ",";
            String val = (String)map.get(key);
            if (!StringTools.isEmptyOrNone(val))
            {
                val = "'+val+'";
                where = where + " AND " + key + " = " + val;
            }
        }
        
        if (StringTools.isEmptyOrNone(columns))
        {
            return null;
        }
        else
        {
            sb.append("select  " + columns + " from " + tableName);
            sb.append(" WHERE 1 = 1 " + where);
        }
        
        System.out.println("生成的SQL语句 ==> "+sb.toString());
        return sb.toString();
    }
    
    /**
     * 生成更新语句
     * <功能详细描述>
     * @param tableName
     * @param t
     * @param clazz
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public String generatorUpdateSQL(String tableName, T t, Class clazz)
    {
        Map<String, Object> map = getObjectValue(t, clazz);
        SQLAnnotation annotation = (SQLAnnotation) clazz.getAnnotation(SQLAnnotation.class);
        
        StringBuffer sb = new StringBuffer();
        String where = "";
        String updateColumns = "";
        for (String key : map.keySet())
        {
            String val = map.get(key).toString();
            if (StringTools.isEmptyOrNone(val))
            {
                continue;
            }
            
            //作为更新的字段
            if (annotation.is_column() && !annotation.update_is_where())
            {
                updateColumns = updateColumns + key + " =" + val + ", ";
            }
            else
            {
                String col = key;
                if (!StringTools.isEmptyOrNone(annotation.where_column()))
                {
                    col = annotation.where_column();
                }
                String tempVal = val;
                if ("like".equals(annotation.where_oper()))
                {
                    
                    tempVal = "%" + val + "%";
                }
                tempVal = "'" + tempVal + "'";
                where = where + col + annotation.where_oper() + tempVal
                        + " AND ";
            }
            
        }
        
        if (StringTools.isEmptyOrNone(where))
        {
            return null;
        }
        else
        {
            sb.append("update " + tableName);
            sb.append(" set " + updateColumns);
            sb.append(" WHERE 1 = 1 " + where);
        }
        return sb.toString();
        
    }
    
    public Map<String, Object> getObjectValue(T t, Class clazz)
    {
        Map<String, Object> value = new HashMap<String, Object>();

        Field[] fields = clazz.getDeclaredFields();

        
        try
        {
            if(t==null){
            t = (T) clazz.newInstance();
            }
            
            for (Field field : fields)
            {
                field.setAccessible(true);
                Object obj = field.get(t);
                
                value.put(field.getName(), obj);
            }
        }
        catch (IllegalArgumentException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (InstantiationException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return value;
        
    }
    
    
    
   
    
}
