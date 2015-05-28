package com.chong.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

public class DataBakutil
{
    //mysql 数据备份
    public static void mysqlDataBak()
    {
        try
        {
            Properties properties = PropertyUtil.getProperties(PropertyUtil.class.getResource("/jdbcConfig.properties")
                    .getPath());
            String url = (String) properties.get("database.url");
            String user = (String) properties.get("database.user");
            String password = (String) properties.get("database.password");
            
            url = url.substring(url.indexOf("//") + 2);
            url = url.substring(0, url.indexOf(":"));
            
            Runtime rt = Runtime.getRuntime();
            String cmd = "mysqldump -h" + url + " -u" + user + " -p" + password
                    + " chong > d:/chongdata/backupfile_"
                    + DateUtil.getNowDate() + ".sql";
            System.out.println("备份命令是====> " + cmd);
            Process p = rt.exec("cmd.exe /c " + cmd);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
            
            System.out.println("結果===》" + bufferedReader.readLine());
        }
        catch (Exception e)
        {
            
        }
        
    }
}
