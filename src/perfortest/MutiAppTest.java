/*
 * 文 件 名:  MutiAppTest.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  pf_wangxiaowei
 * 修改时间:  2014年7月22日
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
 * @author  pf_wangxiaowei
 * @version  [版本号, 2014年7月22日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MutiAppTest
{
	public static final String address = "http://api.heluyou.com/httpopenapi";
	
	public static final String uri = "/auth/nio/serviceManager";
	
	public static final String username = "yudeshui";
	
	public static final String password = "admin";
	
	public static final String userID = "225";
	
	private static Logger logger = Logger.getLogger(MutiAppTest.class);

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
		int count = 100;
		final CountDownLatch counter = new CountDownLatch(count);
		int startSnInx = 0;
		int perThreadRouter = 30;
		
		long timestamp = System.currentTimeMillis();
		for(int i=0; i<count; i++)
		{
			int snStartInx = startSnInx + (i*perThreadRouter);
			Thread th = new Thread(new MutiAppClient(i, snStartInx, perThreadRouter, counter));
			th.start();
		}
        try
        {
            counter.await();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
		timestamp = System.currentTimeMillis() - timestamp;
		logger.debug("===完成消息发送，共发送["+MutiAppClient.sendCount+"]条，"
				+ "接收到["+MutiAppClient.receiveCount+"],"
				+ "错误条数["+MutiAppClient.errorCount+"],"
				+ "耗时["+timestamp+"]ms");
		
	}

}
