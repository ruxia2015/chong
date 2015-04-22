/*
 * 文 件 名:  RouterRequest.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  pf_wangxiaowei
 * 修改时间:  2014年4月24日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package route.core;

import java.io.Serializable;

/**
 * 从OneNet端来的路由器请求
 * <功能详细描述>
 * 
 * @author  pf_wangxiaowei
 * @version  [版本号, 2014年4月24日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RouterRequest implements Serializable
{
    private static final long serialVersionUID = 375567577624203807L;
    
    //源 设备id
    private String sourceId;
    
    //路由器上行消息
    private String message;
    
    public RouterRequest(String sourceId, String message)
    {
        this.sourceId = sourceId;
        this.message = message;
    }
    
    /**
     * @return 返回 sourceId
     */
    public String getSourceId()
    {
        return sourceId;
    }
    
    /**
     * @param 对sourceId进行赋值
     */
    public void setSourceId(String sourceId)
    {
        this.sourceId = sourceId;
    }
    
    /**
     * @return 返回 message
     */
    public String getMessage()
    {
        return message;
    }
    
    /**
     * @param 对message进行赋值
     */
    public void setMessage(String message)
    {
        this.message = message;
    }
    
    /**
     * @return
     */
    @Override
    public String toString()
    {
        return message;
    }
    
}
