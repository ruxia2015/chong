package com.chong.common.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil
{
    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @param path 
     * @param fileName
     * @return [参数说明]
     * 
     * @return File [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static File getFile(String path, String defaultFileName)
    {
        File file = new File(path);
        if (file.exists() && file.isFile())
        {
            return file;
        }
        
        if (!file.exists())
        {
            if (file.getName().contains(".") || StringTools.isEmptyOrNone(defaultFileName))
            {
                File pFile = file.getParentFile();
                if (!pFile.exists())
                {
                    pFile.mkdirs();
                }
            }
            else
            {
                file.mkdirs();
            }
        }
        
        File newFile = new File(path + File.separator + defaultFileName);
        return newFile;
        
    }
    
    public static boolean writeToFile(String path, String defaultFileName,
            String content)
    {
        File file = getFile(path, defaultFileName);
        
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try
        {
            byte[] con = content.getBytes();
            os.write(con);
            os.flush();
            os.writeTo(new FileOutputStream(file));
        }
        catch (FileNotFoundException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        catch (IOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        finally
        {
            
            try
            {
                os.close();
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        return false;
    }
    public static String readFile(String filePath)
    {
        File f = new File(filePath);
        if(!f.exists() || ! f.isFile()){
            System.out.println("文件不存在");
            return null;
        }
        
        try
        {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
            
            byte[] contents = new byte[1024];  
            int byteRead = 0;  
            String strFileContents = "";  
              
            try {  
                while((byteRead = bis.read(contents)) != -1){  
                    strFileContents = strFileContents + new String(contents,0,byteRead);  
                }  
            } catch (IOException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
            
          
            
            return strFileContents;
            
        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        return null;
    }
    
    
    public static void main(String[] args)
    {
        writeToFile("d:\\1", "1.csv", "sd,sd\r\nd,sd");
        String con = readFile("d:\\1\\ResourceAccount_2015-04-20.csv");
        System.out.println(con);
    }


    
}
