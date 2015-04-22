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

public class ResultSetHandler<T>
{
    
    @SuppressWarnings("unchecked")
    public static List resultSetToList(ResultSet rs, Class cls)
            throws Exception
    {
        Field[] fields = cls.getDeclaredFields();
        
        Map<String, Field> fMap = new HashMap<String, Field>();
        for (Field f : fields)
        {
            fMap.put(f.getName().toLowerCase(), f);
        }
        
        List list = new ArrayList();
        
        ResultSetMetaData meta = rs.getMetaData();
        
        Object obj = null;
        while (rs.next())
        {
            obj = cls.newInstance();
            
            for (int i = 1; i <= meta.getColumnCount(); i++)
            {
                //當前列名
                String colName = meta.getColumnName(i);
                
                Field field = fMap.get(colName.toLowerCase());
                if(field==null){
                	continue;
                }
                String fName = field.getName();
                Type fType = field.getType();
                //設置方法名(将字段名的第一个转换成大写，获取其设置的方法)
                String setMethodName = "set"
                        + fName.replaceFirst(fName.substring(0, 1),
                                fName.substring(0, 1).toUpperCase());
                
                Object value = rs.getObject(colName);
                
                if(value==null){
                	continue;
                }
                
                Object val = null;
                
                String fTypeClassName = field.getType().getSimpleName();
                if (fTypeClassName.equalsIgnoreCase("int")
                        || fTypeClassName.equalsIgnoreCase("integer"))
                {
                    val = Integer.valueOf(value.toString());
                }
                else if (fTypeClassName.equalsIgnoreCase("double"))
                {
                    val = Double.valueOf(value.toString());
                }
                else if (fTypeClassName.equalsIgnoreCase("float"))
                {
                    val = Float.valueOf(value.toString());
                }
                else if (fTypeClassName.equalsIgnoreCase("boolean"))
                {
                    val = Boolean.valueOf(value.toString());
                }
                else if (fTypeClassName.equalsIgnoreCase("string"))
                {
                    val =String.valueOf(value);
                }else{
                	continue;
                }
                System.out.println(field.getName());
                System.out.println(field.getType().getTypeName());
                Method method = cls.getMethod(setMethodName,Class.forName(field.getType().getTypeName()));
                method.setAccessible(true);
                method.invoke(obj, val);
                
            }
            
            list.add(obj);
            
        }
        return list;
        
    }
    
    /*  @SuppressWarnings("unchecked")
      public static List resultSetToList(ResultSet rs, Class cls)
              throws Exception
      {
          
          //取得Method 
          Method[] methods = cls.getDeclaredMethods();
          List lst = new ArrayList();
          // 用于获取列数、或者列类型
          ResultSetMetaData meta = rs.getMetaData();
          Object obj = null;
          while (rs.next())
          {
              // 获取formbean实例对象
              obj = cls.newInstance(); // 用Class.forName方法实例化对象和new创建实例化对象是有很大区别的，它要求JVM首先从类加载器中查找类，然后再实例化，并且能执行类中的静态方法。而new仅仅是新建一个对象实例
              // 循环获取指定行的每一列的信息
              for (int i = 1; i <= meta.getColumnCount(); i++)
              {
                  // 当前列名
                  String colName = meta.getColumnName(i);
                  
                  // 设置方法名
                  String setMethodName = "set" + colName;
                  
                  //遍历Method 
                  for (int j = 0; j < methods.length; j++)
                  {
                      if (methods[j].getName().equalsIgnoreCase(setMethodName))
                      {
                          setMethodName = methods[j].getName();
                          
                          System.out.println(setMethodName);
                          // 获取当前位置的值，返回Object类型
                          Object value = rs.getObject(colName);
                          if (value == null)
                          {
                              continue;
                          }
                          
                          //实行Set方法 
                          try
                          {
                              //// 利用反射获取对象
                              //JavaBean内部属性和ResultSet中一致时候 
                              Method setMethod = obj.getClass()
                                      .getMethod(setMethodName, value.getClass());
                              setMethod.invoke(obj, value);
                          }
                          catch (Exception e)
                          {
                              //JavaBean内部属性和ResultSet中不一致时候，使用String来输入值。 
                              e.printStackTrace();
                          }
                      }
                  }
              }
              lst.add(obj);
          }
          
          return lst;
          
      }*/
}
