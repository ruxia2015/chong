/*
 * 文 件 名:  PropertyUtil.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  Administrator
 * 修改时间:  2014年4月11日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.chong.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取配置文件/config/config.properties的工具类
 * 
 * 此工具类会动态读取配置文件，配置文件修改后会重新加载配置项。
 * @author  Administrator
 * @version  [版本号, 2014年4月11日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class PropertyUtil
{
    private Properties props = null;
    
    //配置文件
    public File configFile;
    
    //配置文件的最后修改时间
    public long fileLastModify = 0L;
    
    public PropertyUtil(String path)
    {
        try
        {
            props = new Properties();
            configFile = new File(path);
            load();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * 根据配置文件中的key,查找值
     * 
     * @param name
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public String getValue(String name)
    {
        long lm = configFile.lastModified();
        if (lm != fileLastModify)
        {
            load();
        }
        return props.getProperty(name);
    }
    
    /**
     * 根据,返回多组数据
     * <功能详细描述>
     * @param name
     * @return [参数说明]
     * 
     * @return String[] [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public String[] getValue4Array(String name)
    {
        long lm = configFile.lastModified();
        if (lm != fileLastModify)
        {
            load();
        }
        String perperties = props.getProperty(name);
        String[] valueArray = null;
        if (!StringTools.isEmptyOrNone(perperties))
        {
            valueArray = perperties.split(",");
        }
        return valueArray;
    }
    
    /**
     * 重新加载配置文件配置项
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private void load()
    {
        try
        {
            InputStream is = new FileInputStream(configFile);
            props.load(is);
            fileLastModify = configFile.lastModified();
            is.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @param path
     * @param url
     * @param uri
     * @param message
     * @param expectEntity  为*清空，有则修改，无则不修改
     * @param canDelete
     * @param remark
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static String updateProperties(String path, String url, String uri,
            String message, String expectEntity, String canDelete,
            String remark)
    {
        String codeDes = "succse";
        Properties prop = new Properties();// 属性集合对象 
        FileInputStream fis;
        try
        {
            fis = new FileInputStream(path);
            prop.load(fis);
            fis.close();
        }
        catch (FileNotFoundException e)
        {
            codeDes = "找不到指定的properties文件.";
            e.printStackTrace();
            return codeDes;
        }
        catch (IOException e)
        {
            codeDes = "修改properties文件失败.";
            e.printStackTrace();
            return codeDes;
        }
        
        if (!StringTools.isEmptyOrNone(url))
        {
            // 修改url的属性值 
            prop.setProperty("url", url);
        }
        
        if (!StringTools.isEmptyOrNone(uri))
        {
            // 修改uri的属性值 
            prop.setProperty("uri", uri);
        }
        
        if (!StringTools.isEmptyOrNone(message))
        {
            // 修改message的属性值 
            prop.setProperty("message", message);
        }
        
        //如果为*，则清空，否则无则不修改
        if ("*".equals(expectEntity))
        {
            prop.setProperty("expectEntity", "");
        }
        else if (!StringTools.isEmptyOrNone(expectEntity))
        {
            // 修改expectEntity的属性值 
            prop.setProperty("expectEntity", expectEntity);
        }
        
        if (!StringTools.isEmptyOrNone(canDelete))
        {
            // 修改canDelete的属性值 
            prop.setProperty("canDelete", canDelete);
        }
                
                if (!StringTools.isEmptyOrNone(remark))
                {
                    // 修改remark的属性值 
                    prop.setProperty("remark", remark);
                }
        
        // 文件输出流 
        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(path);
            prop.store(fos, "Copyright (c) Boxcode Studio");
            fos.flush();
            fos.close();// 关闭流 
        }
        catch (FileNotFoundException e1)
        {
            codeDes = "找不到指定的propertise文件.";
            e1.printStackTrace();
            return codeDes;
        }
        catch (IOException e)
        {
            codeDes = "修改propertise文件失败。";
            e.printStackTrace();
            return codeDes;
        }
        finally
        {
            
            try
            {
                fos.close();
                fis.close();
            }
            catch (IOException e)
            {
                codeDes = "关闭IO失败。";
                e.printStackTrace();
                return codeDes;
            }
        }
        return codeDes;
    }
    
    /**
     * 写入properties
     * <功能详细描述>
     * @param file
     * @param url
     * @param uri
     * @param message
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static String insetProperties(File file, String url, String uri,
            String message, String messageType, String expectEntity,
            String canDelete, String remark)
    {
        String codeDes = "succse";
        Properties prop = new Properties();// 属性集合对象 
        
        // 修改url的属性值 
        prop.setProperty("url", url);
        
        // 修改uri的属性值 
        prop.setProperty("uri", uri);
        
        // 修改message的属性值 
        prop.setProperty("message", message);
        
        // 修改message的属性值 
        prop.setProperty("messageType", messageType);
        
        if (!StringTools.isEmptyOrNone(expectEntity))
        {
            //预期消息
            prop.setProperty("expectEntity", expectEntity);
        }
        
        // 修改canDelete的属性值 
        prop.setProperty("canDelete", canDelete);
        
        // 修改remark的属性值 
                prop.setProperty("remark", remark);
        
//        // 文件输出流 
        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(file);
            prop.store(fos, "Copyright (c) Boxcode Studio");
            fos.flush();
            // 关闭流 
            fos.close();
        }
        catch (FileNotFoundException e1)
        {
            codeDes = "不能找到properties文件";
            e1.printStackTrace();
            return codeDes;
        }
        catch (IOException e)
        {
            codeDes = "修改文件失败。";
            e.printStackTrace();
            return codeDes;
        }
        finally
        {
            
            try
            {
                fos.close();
            }
            catch (IOException e)
            {
                codeDes = "关闭IO失败。";
                e.printStackTrace();
                return codeDes;
            }
        }
        return codeDes;
    }
    
    /**
     * 获取文件的配置
     * <功能详细描述>
     * @param path
     * @return [参数说明]
     * 
     * @return Properties [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static Properties getProperties(String path)
    {
        Properties prop = new Properties();// 属性集合对象 
        FileInputStream fis;
        try
        {
            fis = new FileInputStream(path);
            prop.load(fis);
            fis.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        return prop;
    }
    
    /**
     * 修改properties
     * <功能详细描述>
     * @param newFile
     * @param prop
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static String updateNewFile(File newFile, Properties prop)
    {
        String returnDesc = "success";
        
        try
        {
            newFile.createNewFile();
        }
        catch (IOException e)
        {
            returnDesc = "创建文件失败，请检查目录情况。";
            e.printStackTrace();
            return returnDesc;
        }
        
        // 文件输出流 
        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(newFile);
            prop.store(fos, "Copyright (c) Boxcode Studio");
            fos.flush();
            // 关闭流 
            fos.close();
        }
        catch (FileNotFoundException e1)
        {
            returnDesc = "找不到要写入的文件.";
            e1.printStackTrace();
            return returnDesc;
        }
        catch (IOException e)
        {
            returnDesc = "写入文件失败。";
            e.printStackTrace();
            return returnDesc;
        }
        finally
        {
            try
            {
                fos.close();
            }
            catch (IOException e)
            {
                returnDesc = "关闭文件流失败。";
                e.printStackTrace();
                return returnDesc;
            }
        }
        return returnDesc;
        
    }
}
