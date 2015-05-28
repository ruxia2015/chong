package com.chong.listener.Thread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;

import com.chong.common.jdbc.JdbcTemplate;
import com.chong.common.util.DataBakutil;
import com.chong.common.util.DateUtil;
import com.chong.common.util.PropertyUtil;
import com.chong.listener.AutoRunConfig;

public class BackDatabaseThread implements Runnable
{
    
    @Override
    public void run()
    {
        while (AutoRunConfig.autoBak)
        {
            try
            {
                //                Connection connection = JdbcTemplate.getConnection();
                //                Statement statement = connection.createStatement();
                //                String sql = "ysqldump --all-databases -h127.0.0.1 -uroot -ppass > allbackupfile.sql";
                //                statement.execute(sql);
                
                //备份所有的数据                
                DataBakutil.mysqlDataBak();
                
                Thread.sleep(1 * 24 * 60 * 60 * 1000L);
            }
            catch (Exception e)
            {
                // TODO: handle exception
            }
        }
    }
    
}
