package perfortest;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentProducer;
import org.apache.http.entity.EntityTemplate;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.util.EntityUtils;

import content.DigestAuthUtils;
import content.KeyUtil;

/**
 * 
 * 自动化测试工具
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年7月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@SuppressWarnings("deprecation")
public class ThreadTest implements Runnable
{
    private CountDownLatch counter;
    /**
     * http请求url
     */
    public static final String URL = "http://api.heluyou.com/httpopenapi";
    
    /**
     * http请求uri
     */
    public static final String URI = "/auth/nio/serviceManager";
    
    /**
     * 用户名
     */
    public static final String userName = "yudeshui";
    
    /**
     * 密码
     */
    public static final String passWord = "admin";
    
    public static int devID = 0;
    
    /**
     * 消息体
     */
    public String message = "{\"version\":16,\"msgType\":\"MSG_GET_ROUTER_STATUS_REQ\",\"msgSeq\":\"0\",\"devID\":\""+ "SN-9000" + (devID += 1) + "\",\"userID\":\"225\"}";

    public static int SENDSEQUENS = 0;
    
    public static Object lock = new Object();
    
    public static Object lockForDevID = new Object();
    
    //包装消息体
    public static BlockingQueue<String> queues = new LinkedBlockingQueue<String>();
    
    public ThreadTest(CountDownLatch counter)
    {
        super();
        this.counter = counter;
    }

    /**
     * 线程启动方法
     */
    public void run()
    {
        int thisTheadID = AppTest.sendMessageCount += 1;
        
        //调用具体处理方法
        test(thisTheadID);
        counter.countDown();
    }
    
    /**
     * 具体处理逻辑
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public void test(int therdID)
    {
        
        for(int k = 0; k < 60; k++)
        {
          //获取开始时间
            long startTime = System.currentTimeMillis();
            
            //首次挑战
            HttpClient client = new DefaultHttpClient(
                    new ThreadSafeClientConnManager());
            
            HttpPost method = new HttpPost(URL + URI);
            
            try
            {
                DigestData data = new DigestData();
                data.setUsername(userName);
                data.setPassword(passWord);
                StringBuilder firstHead = new StringBuilder();
                firstHead.append("");
                firstHead.append("Digest ");
                firstHead.append("username=\"" + data.getUsername() + "\"");
                firstHead.append(", uri=\"uri\"");
                firstHead.append(", algorithm=\"MD5\"");
                method.setHeader("Authorization", firstHead.toString());
                
                //发送消息
                HttpResponse httpResponse = client.execute(method);
                
                //            System.out.println("第一次挑战结果:"
                //                    + httpResponse.getStatusLine().toString());
                Header[] herders = httpResponse.getAllHeaders();
                //            for (Header h : herders)
                //            {
                //                System.out.println(h.getName() + ":" + h.getValue());
                //            }
                
                int times = 1;
                
                if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_UNAUTHORIZED)
                {
                    //获取挑战后的鉴权信息
                    Header header = httpResponse.getHeaders("WWW-Authenticate")[0];
                    HeaderElement[] eles = header.getElements();
                    times = 1;
                    for (int i = 0; i < eles.length; i++)
                    {
                        HeaderElement ele = eles[i];
                        String name = null;
                        if (i == 0)
                        {
                            if (!ele.getName().startsWith("Digest"))
                            {
                                throw new RuntimeException("not digest auth");
                            }
                            name = ele.getName().substring(7);
                        }
                        else
                        {
                            name = ele.getName();
                        }
                        if ("realm".equals(name))
                        {
                            data.setRealm(ele.getValue());
                        }
                        else if ("nonce".equals(name))
                        {
                            data.setNonce(ele.getValue());
                        }
                        else if ("qop".equals(name))
                        {
                            data.setQop(ele.getValue());
                        }
                        else if ("opaque".equals(name))
                        {
                            data.setOpaque(ele.getValue());
                        }
                    }
                }
                else if (URI.startsWith("/auth")
                        && null != httpResponse.getHeaders("Authorization-Info")
                        && httpResponse.getHeaders("Authorization-Info").length > 0)
                {
                    Header header = httpResponse.getHeaders("Authorization-Info")[0];
                    HeaderElement[] eles = header.getElements();
                    for (int i = 0; i < eles.length; i++)
                    {
                        HeaderElement ele = eles[i];
                        String name = ele.getName();
                        if ("nextnonce".equals(name))
                        {
                            data.setNonce(ele.getValue());
                            break;
                        }
                    }
                }
                
                //生成请求鉴权头，第二次发起请求
                data.setUri(URI);
                data.setCnonce(KeyUtil.generateCnonce());
                String responseStr = DigestAuthUtils.generateResponse(data, "POST");
                StringBuilder hv = new StringBuilder();
                hv.append("Digest ");
                hv.append("username=\"" + data.getUsername() + "\"");
                hv.append(", realm=\"" + data.getRealm() + "\"");
                hv.append(", nonce=\"" + data.getNonce() + "\"");
                hv.append(", uri=\"" + data.getUri() + "\"");
                hv.append(", algorithm=\"MD5\"");
                hv.append(", response=\"" + responseStr + "\"");
                hv.append(", opaque=\"" + data.getOpaque() + "\"");
                hv.append(", qop=\"" + data.getQop() + "\"");
                hv.append(", cnonce=\"" + data.getCnonce() + "\"");
                hv.append(", nc=\"" + String.format("%08d", times) + "\"");
                
                method.setHeader("Authorization", hv.toString());
                ContentProducer cp = new ContentProducer()
                {
                    @Override
                    public void writeTo(OutputStream outstream) throws IOException
                    {
                        Writer writer = new OutputStreamWriter(outstream, "UTF-8");
                        writer.write(getDeviceID());
                        writer.flush();
                    }
                };
                method.setEntity(new EntityTemplate(cp));
                httpResponse = client.execute(method);
                times++;
//                System.out.println("第" + times + "返回code:"
//                        + httpResponse.getStatusLine().toString());
                herders = httpResponse.getAllHeaders();
                
                //获取开始时间
                long endTime = System.currentTimeMillis();
                
                String result = null;
                if (String.valueOf(httpResponse.getStatusLine().getStatusCode())
                        .startsWith("20"))
                {
                    result = EntityUtils.toString(httpResponse.getEntity());
                }
                else
                {
                    result = httpResponse.toString();
                }
//                
//                StringBuffer sb = new StringBuffer();
//                for (Header h : herders)
//                {
//                    sb.append(h.getName() + ":" + h.getValue());
//                }
                //            System.out.println(sb.toString());
                //            System.out.println(result);
                
                String testMessage = null;
                synchronized (lock)
                {
                    testMessage = "性能测试" + (SENDSEQUENS += 1) + "{" + "[线程分配ID:" + String.valueOf(therdID) + "]"
                            + "[启动时间:" + String.valueOf(startTime) + "]" + "[结束时间：" + String.valueOf(endTime)
                            + "]" + "[耗时:" + String.valueOf(endTime - startTime) + "]" + "[返回结果:" + result + "]" + "}";
                }

                queues.add(testMessage);
            }
            
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        
    }
    
    /**
     * 获取当前时间
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return long [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public long getNowTime()
    {
        long longTime = Long.valueOf(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
        return longTime;
    }
    
    /**
     * 获取设备ID
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public String getDeviceID()
    {
        String message = null;
        synchronized (lockForDevID)
        {
            message = "{\"version\":16,\"msgType\":\"MSG_GET_ROUTER_STATUS_REQ\",\"msgSeq\":\"0\",\"devID\":\""+ "86999900000014000000SN-9000" + (devID += 1) + "\",\"userID\":\"225\"}";
        }
        return message;
        
    }
}
