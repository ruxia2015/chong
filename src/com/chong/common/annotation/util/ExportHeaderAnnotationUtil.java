package com.chong.common.annotation.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chong.common.annotation.ExportHeaderAnnotation;
import com.chong.common.util.StringTools;

public class ExportHeaderAnnotationUtil
{
    
    public static Map<String, String> getFieldName(Class cls)
    {
        Map<String,String> map = new HashMap<String, String>();
        Field[] fields = cls.getDeclaredFields();
        for (Field f : fields)
        {
            ExportHeaderAnnotation annotation = (ExportHeaderAnnotation) AnnontationUtil.getFieldAnnotation(f,
                    ExportHeaderAnnotation.class);
            
            if (annotation != null && annotation.isIngore())
            {
                continue;
            }
            else if (annotation != null
                    && StringTools.isEmptyOrNone(annotation.headerName()))
            {
                map.put(f.getName(), annotation.headerName());                
                
            }
            else
            {
                map.put(f.getName(), annotation.headerName()); 
            }
            
            
        }
        
        return map;
        
    }
    
//    public static Object setObjectValue(Object object,String headerName){
//        
//    }
    
    
    public static String getHeaders(Class cls)
    {
        
        Field[] fields = cls.getDeclaredFields();
        
        String header = "";
        for (Field f : fields)
        {
            f.setAccessible(true);
            ExportHeaderAnnotation annotation = (ExportHeaderAnnotation) AnnontationUtil.getFieldAnnotation(f,
                    ExportHeaderAnnotation.class);
            
            if (annotation != null && annotation.isIngore())
            {
                continue;
            }
            else if (annotation != null
                    && !StringTools.isEmptyOrNone(annotation.headerName()))
            {
                header = header + annotation.headerName() + ",";
            }
            else
            {
                header = header + f.getName() + ",";
            }
            
        }
        
        return header;
    }
    
    /**
     * 获取字段的内容
     * <功能详细描述>
     * @param obj
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static List<String> getContent(Object obj)
    {
        if (obj == null)
        {
            return null;
        }
        
        Class cls = obj.getClass();
        
        Field[] fields = cls.getDeclaredFields();
        
        List<String> row = new ArrayList<String>();
        Object col = "";
        
        try
        {
            for (Field f : fields)
            {
                ExportHeaderAnnotation annotation = (ExportHeaderAnnotation) AnnontationUtil.getFieldAnnotation(f,
                        ExportHeaderAnnotation.class);
                
                f.setAccessible(true);
                
                if (annotation == null)
                {
                    col = f.get(obj);
                }
                else if (annotation.isIngore())
                {
                    continue;
                }
                else if (annotation.needConvert())
                {
                    col = f.get(obj);
                    
                    String[] str = annotation.convertKeys();
                    String[] values = annotation.convertValues();
                    
                    if (str != null && str.length > 0 && values != null
                            && values.length > 0)
                    {
                        for (int i = 0; i < str.length && i<values.length; i++)
                        {
                            if(col==null){
                                col = "";
                            }
                            
                            
                            if (str[i].equals(col))
                            {
                                col = values[i];
                                break;
                            }
                        }
                    }
                    
                }
                else
                {
                    col = f.get(obj);
                    
                }
                
                if (col == null)
                {
                    row.add("");
                }
                else
                {
                    row.add(col + "");
                }
                
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
        
        return row;
    }
    
}
