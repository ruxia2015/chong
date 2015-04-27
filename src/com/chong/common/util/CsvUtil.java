package com.chong.common.util;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.chong.common.annotation.util.ExportHeaderAnnotationUtil;

public class CsvUtil
{
    
    
    public static File wirteToCsv(List objs, String fiePath)
    {
        
        String filename = "";
        String content = getCsvContent(objs);
        
        filename = filename + "_" + DateUtil.getNowDate() + ".csv";
        
        //写入文件
        FileUtil.writeToFile(fiePath, filename, content);
        
        System.err.println(content);
        
        return null;
    }

    public static String getCsvContent(Collection objs)
    {
        String content = "";
        boolean isHave = false;
        for (Object obj : objs)
        {
            Class cls = obj.getClass();
            if (StringTools.isEmptyOrNone(content))
            {
                content = ExportHeaderAnnotationUtil.getHeaders(cls) + "\r\n";
            }
            List row = ExportHeaderAnnotationUtil.getContent(obj);
            
            content = content + StringUtils.join(row, ",") + "\r\n";
        }
        return content;
    }
    
    //第一列为字段名称
    public static List readCsv(Class cls, String file)
    {
        List res = new ArrayList();
        
        String content = FileUtil.readFile(file);
        if(StringTools.isEmptyOrNone(content)){
            return null;
        }
        
        String[] rows = content.split("\n");
        if (rows != null && rows.length > 1)
        {
            //获取标题对应的名称
            Map<String, String> headerMap = ExportHeaderAnnotationUtil.getFieldName(cls);
            String[] headers = rows[0].split(",");
            
            for (int i = 0; i < headers.length; i++)
            {
                String str = headers[i];
                if (StringTools.isEmptyOrNone(str))
                {
                    continue;
                }
                
                str = str.trim();
                
                if (StringTools.isEmptyOrNone(headerMap.get(str)))
                {
                    
                    for (String temp : headerMap.keySet())
                    {
                        if (str.equals(headerMap.get(temp)))
                        {
                            headers[i] = headerMap.get(temp);
                        }
                    }
                }
                
            }
            
            try{
            for (int i=1;i<rows.length;i++)
            {
                Object obj = cls.newInstance();
                
                String[] cols =rows[i].split(",");
                
                for(int j=0;j<cols.length;j++){
                  Field f =  cls.getDeclaredField(headers[j]);
                  f.setAccessible(true);
                  f.set(obj, cols[i]);
                  
                }
                
                res.add(obj);
                
            }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
         return res;   
        
    }
    
    public static void main(String[] args)
    {
       System.out.print("1,2,,,d,".split(",").length);
        
    }


    
}
