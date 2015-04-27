/*
 * 文 件 名:  Connection.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  pf_wangxiaowei
 * 修改时间:  2014年4月21日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package route.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

/**
 * 长连接对象
 * <功能详细描述>
 * 
 * @author  pf_wangxiaowei
 * @version  [版本号, 2014年4月21日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class Connection
{
    private Logger logger = Logger.getLogger(this.getClass());
    
    //实例名
    public static Connection connection = null;
    
    //长连接状态：已连接
    public static final int CONNECTED = 0x01;
    
    //长连接状态：正在连接或重连接
    public static final int CONNECTING = 0x02;
    
    //长连接状态：连接断开
    public static final int DIS_CONNECT = 0x03;
    
    //连接套接字
    private static Socket socket = null;
    
    private Lock lock = new ReentrantLock();
    
    //长连接状态
    volatile int connState = CONNECTING;
    
    private long receiveTime = System.currentTimeMillis();
    
    private long sendTime = 0;
    
    private long sendDataTime = 0;
    
    private String key;
    
    private Receiver receiver;
    
    public static Connection getConnection()
    {
        if (connection != null)
        {
            return connection;
        }
        return null;
    }
    
    /**
     * 连接初始化
     * <功能详细描述>
     * @param ip
     * @param point [参数说明]
     * 
     * @return void [返回类型说明]
     * @throws Exception 
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static Receiver init(String ip, String point,String userId,String devSn,String mac) throws Exception
    {
        //测试时修改下设备id
        String deviceId = "15107";
        socket = connect(ip, point);
        connection = new Connection(deviceId, socket);
        Receiver receiver = new Receiver(connection);
        connection.setReceiver(receiver);
        String authInfo = "{\"SN\":\""+devSn+"\",\"MAC\":\""+mac+"\"}"; 
        
        //发送连接请求
        ConnectionManager.sendConnReq(connection,
                userId,
                authInfo);
        
        //启动接收线程
        receiver.startup();
        
        //启动发送心跳
        ConnectionManager.addConnection(connection);
        
        return receiver;
    }
    
    public Connection(String key, Socket socket)
    {
        super();
        this.key = key;
        this.socket = socket;
    }
    
    /**
     * 发送数据
     * <功能详细描述>
     * @param data
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public synchronized boolean sendMessage(byte[] data) throws SocketException
    {
        if (connState == DIS_CONNECT)
        {
            return false;
        }
        try
        {
            socket.getOutputStream().write(data);
            socket.getOutputStream().flush();
        }
        catch (SocketException e)
        {
            throw e;
        }
        catch (IOException e)
        {
            logger.error(e);
            return false;
        }
        return true;
    }
    
    /**
     * 是否正常连接
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean isConnected()
    {
        return connState == CONNECTED;
    }
    
    /**
     * 是否正在重连接
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean isReConnecting()
    {
        return connState == CONNECTING;
    }
    
    /**
     * 是否已经断连
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean isDisConnected()
    {
        return connState == DIS_CONNECT;
    }
    
    /**
     * 将连接状态设置为【已连接】.
     * 此连接会加同步锁
     *  
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public void setStateConnected()
    {
        lock.lock();
        connState = Connection.CONNECTED;
        lock.unlock();
    }
    
    /**
     * 将连接状态由【已连接】设置为【断连】.
     * 此连接会加同步锁，如果频繁调用，请在调用前，请加判断条件 
     * if(ConnectionManager.isConnected())
     * {
     * 		ConnectionManager.stateConnToDisconn();
     * }
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public void stateConnToDisconn()
    {
        lock.lock();
        if (this.isConnected())
        {
            connState = Connection.DIS_CONNECT;
        }
        lock.unlock();
    }
    
    /**
     * 将连接状态由【断连】设置为【连接中】.
     * 此连接会加同步锁，如果频繁调用，请在调用前，请加判断条件 
     * if(ConnectionManager.isDisconnected())
     * {
     * 		ConnectionManager.stateDisconnToConn();
     * }
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public void stateDisconnToConn()
    {
        lock.lock();
        if (this.isDisConnected())
        {
            connState = Connection.CONNECTED;
        }
        lock.unlock();
    }
    
    /**
     * 将连接状态由【连接中】设置为【断连】.
     * 此连接会加同步锁，如果频繁调用，请在调用前，请加判断条件 
     * if(ConnectionManager.isConnecting())
     * {
     * 		ConnectionManager.stateConningToDisConn();
     * }
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public void stateConningToDisConn()
    {
        lock.lock();
        if (this.isDisConnected())
        {
            connState = Connection.CONNECTED;
        }
        lock.unlock();
    }
    
    /**
     * 获取输入流
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return InputStream [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public InputStream getInputStream()
    {
        if (this.connState == DIS_CONNECT)
        {
            return null;
        }
        try
        {
            if (socket != null && socket.isConnected()
                    && !socket.isInputShutdown())
            {
                return socket.getInputStream();
            }
        }
        catch (IOException e)
        {
            logger.error("获取连接输入流失败:", e);
        }
        
        return null;
    }
    
    public void close()
    {
        if (socket != null)
        {
            try
            {
                socket.close();
            }
            catch (IOException e)
            {
                logger.error(e);
            }
        }
    }
    
    public void updateHeartTime()
    {
        this.receiveTime = System.currentTimeMillis();
    }
    
    //获取连接
    public static Socket connect(String ip, String port) throws Exception
    {
        Socket socket = null;
        try
        {
            //新建立连接，并将状态设置为正在重连
            socket = new Socket(ip, Integer.valueOf(port));
            socket.setKeepAlive(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        return socket;
    }
    
    /**
     * @return 返回 receiveTime
     */
    public long getReceiveTime()
    {
        return receiveTime;
    }
    
    /**
     * @return 返回 key
     */
    public String getKey()
    {
        return key;
    }
    
    /**
     * @return 返回 receiver
     */
    public Receiver getReceiver()
    {
        return receiver;
    }
    
    /**
     * @param 对receiver进行赋值
     */
    public void setReceiver(Receiver receiver)
    {
        this.receiver = receiver;
    }
    
    /**
     * @return 返回 sendTime
     */
    public long getSendTime()
    {
        return sendTime;
    }
    
    /**
     * @param 对sendTime进行赋值
     */
    public void setSendTime(long sendTime)
    {
        this.sendTime = sendTime;
    }
    
    /**
     * @return 返回 sendDataTime
     */
    public long getSendDataTime()
    {
        return sendDataTime;
    }
    
    /**
     * @param 对sendDataTime进行赋值
     */
    public void setSendDataTime(long sendDataTime)
    {
        this.sendDataTime = sendDataTime;
    }
    
}
