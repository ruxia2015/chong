/*
 * 文 件 名:  AppTest.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  Administrator
 * 修改时间:  2014年7月16日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package perfortest;

import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;


/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年7月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AppTest
{
    private static Logger logger = Logger.getLogger(AppTest.class);
    
    /**
     * 设置初始化
     */
    public static int sendMessageCount = 0;
    
    /** <一句话功能简述>
     * <功能详细描述>
     * @param args [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void main(String[] args)
    {
        long startTime = System.currentTimeMillis();
        System.out.println("StartTime:" + startTime);
        int count = 50;
        final CountDownLatch counter = new CountDownLatch(count);
        for (int i = 0; i < count; i++)
        {
            new Thread(new ThreadTest(counter)).start();
        }
        try
        {
            counter.await();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        logger.debug("EndTime:" + endTime);
        logger.debug(count + "条消息消耗时间：" + (endTime - startTime));
        logger.debug("平均消耗时间：" + ((endTime - startTime)/500));
        
        for (int i = 0; i < ThreadTest.queues.toArray().length ; i++)
        {
            logger.debug(ThreadTest.queues.toArray()[i]);
        }
    }
    
}
