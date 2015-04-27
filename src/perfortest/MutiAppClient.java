/*
 * 文 件 名:  MutiAppClient.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  pf_wangxiaowei
 * 修改时间:  2014年7月22日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package perfortest;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.concurrent.CountDownLatch;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentProducer;
import org.apache.http.entity.EntityTemplate;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import content.DigestAuthUtils;
import content.KeyUtil;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  pf_wangxiaowei
 * @version  [版本号, 2014年7月22日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MutiAppClient implements Runnable
{
	private Logger logger = Logger.getLogger(this.getClass());
	
	private int threadInx;//线程标识
	
	private int handRouterNum = 20;//处理的路由器个数
	
	private int snStratInx;//sn的开始标识
	
	private CountDownLatch counter;
	
	public static int receiveCount = 0;
	
	public static int sendCount = 0;
	
	public static int errorCount = 0;
		
	private static Object receiveLock = new Object();
	
	private static Object sendLock = new Object();
	
	private static Object errorLock = new Object();
	
	public MutiAppClient(int threadInx, int snStartInx, int num,CountDownLatch counter)
	{
		this.threadInx = threadInx;
		this.snStratInx = snStartInx;
		this.handRouterNum = num;
		this.counter = counter;
	}

	
	
	@Override
	public void run()
	{
		DigestData data = firstChall();
		
		int times = 1;
		for(int i=snStratInx; i<(snStratInx+handRouterNum); i++)
		{
			final String message = this.getSendMessage(i);
			HttpClient client = new DefaultHttpClient();        
	        HttpPost method = new HttpPost(MutiAppTest.address + MutiAppTest.uri);
	        data.setCnonce(KeyUtil.generateCnonce());			
			
			method.setHeader("Authorization", this.generateHeader(data, times));	
			
			ContentProducer cp = new ContentProducer()
			{				        
		        @Override
		        public void writeTo(OutputStream outstream) throws IOException
		        {
		            Writer writer = new OutputStreamWriter(outstream, "UTF-8");
			        writer.write(message);
			        writer.flush();
			        
			    }
			};
			method.setEntity(new EntityTemplate(cp));
			try
			{
				int send = 0;
		        synchronized(sendLock)
		        {
		        	sendCount ++;
		        	send = sendCount;
		        }
		        logger.info("线程["+threadInx+"]发起了第["+send+"]条消息。");
		        
				HttpResponse response = client.execute(method);
				if(response.getStatusLine().getStatusCode()==HttpStatus.SC_UNAUTHORIZED)
				{
					Header header = response.getHeaders("WWW-Authenticate")[0];
					HeaderElement[] eles = header.getElements();			
					for(int j=0; j<eles.length; j++)
					{
						HeaderElement ele = eles[j];
						if("nonce".equals(ele.getName()))
						{
							data.setNonce(ele.getValue());
							break;
						}
					}
					logger.debug("=====鉴权失败，重新发起一次请求=====");
					method.setHeader("Authorization", this.generateHeader(data, times));	
					method.setEntity(new EntityTemplate(cp));
					response = client.execute(method);
				}
				Header header = response.getHeaders("Authorization-Info")[0];
				HeaderElement[] eles = header.getElements();					
				for(int j=0; j<eles.length; j++)
				{
					HeaderElement ele = eles[j];
					String name = ele.getName();
					if("nextnonce".equals(name))
					{
						data.setNonce(ele.getValue());
						break;
					}
				}
				int receive = 0;
				synchronized(receiveLock)
				{
					receiveCount++;
					receive = receiveCount;
				}
				logger.info("接收到第["+receive+"]条消息："+EntityUtils.toString(response.getEntity()));
			} 
			catch (Exception e)
			{
				int error = 0;
				synchronized(errorLock)
				{
					errorCount++;
					error = errorCount;					
				}
				logger.error("发送第["+error+"]条消息时错误",e);
			}
			method.releaseConnection();
		}
		
		counter.countDown();
	}
	
	private String generateHeader(DigestData data, int times)
	{
		String responseStr = DigestAuthUtils.generateResponse(data, "POST");
		StringBuilder hv = new StringBuilder();
		hv.append("Digest ");
		hv.append("username=\""+data.getUsername()+"\"");
		hv.append(", realm=\""+data.getRealm()+"\"");
		hv.append(", nonce=\""+data.getNonce()+"\"");
		hv.append(", uri=\""+data.getUri()+"\"");
		hv.append(", algorithm=\"MD5\"");
		hv.append(", response=\""+responseStr+"\"");
		hv.append(", opaque=\""+data.getOpaque()+"\"");
		hv.append(", qop=\""+data.getQop()+"\"");
		hv.append(", cnonce=\""+data.getCnonce()+"\"");
		hv.append(", nc=\""+String.format("%08d", times)+"\"");
		return hv.toString();
	}
	
	/**
	 * 首次挑战
	 * <功能详细描述>
	 * @return [参数说明]
	 * 
	 * @return DigestData [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	private DigestData firstChall()
	{		
		DigestData data = new DigestData();
		data.setUsername(MutiAppTest.username);
		data.setPassword(MutiAppTest.password);
		
		HttpClient client = new DefaultHttpClient();        
        HttpPost method = new HttpPost(MutiAppTest.address + MutiAppTest.uri);
        try
		{
			HttpResponse response = client.execute(method);
			Header header = response.getHeaders("WWW-Authenticate")[0];
			HeaderElement[] eles = header.getElements();			
			for(int i=0; i<eles.length; i++)
			{
				HeaderElement ele = eles[i];
				String name = null;
				if(i==0)
				{
					if(!ele.getName().startsWith("Digest "))
					{
						throw new RuntimeException("not digest auth");
					}
					name = ele.getName().substring(7);						
				}
				else
				{
					name = ele.getName();
				}
				if("realm".equals(name))
				{
					data.setRealm(ele.getValue());
				}
				else if("nonce".equals(name))
				{
					data.setNonce(ele.getValue());
				}
				else if("qop".equals(name))
				{
					data.setQop(ele.getValue());
				}
				else if("opaque".equals(name))
				{
					data.setOpaque(ele.getValue());
				}
			}
		} 
        catch (Exception e)
		{
			logger.error("首次挑战失败：", e);
			return null;
		}		
        return data;
	}

	public String getSendMessage(int snInx)
	{
		StringBuilder message = new StringBuilder();
		message.append("{\"version\":16,\"msgType\":\"MSG_GET_ROUTER_STATUS_REQ\",\"msgSeq\":\"0\",\"devID\":\"");
		message.append("86999900000014000000SN-9000" + snInx + "\",\"userID\":\""+MutiAppTest.userID+"\"}");
		return message.toString();
	}
}
