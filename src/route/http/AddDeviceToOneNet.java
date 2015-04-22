/*
 * 文 件 名:  OneNetHttpClient.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  pf_wangxiaowei
 * 修改时间:  2014年5月5日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package route.http;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentProducer;
import org.apache.http.entity.EntityTemplate;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import route.RouterConstant;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  pf_wangxiaowei
 * @version  [版本号, 2014年5月5日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AddDeviceToOneNet
{

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
		HttpClient client = new DefaultHttpClient();
		HttpPost method = new HttpPost("http://10.189.24.66/devices");
		try
		{
			method.setHeader("api-key", "RnCt7UNiutCOFWL4ImhjN5btG9I=");
			final StringBuilder message = new StringBuilder();	
			message.append("{\"title\":\"Router\",");
			message.append("\"desc\":\"Router\",");
			message.append("\"tags\":[\"Tag1\",\"Tag2\"],");
			message.append("\"location\":{\"ele\":370000,\"lat\":17.609997,\"lon\":177.03403},");
			message.append("\"private\":true,");
			message.append("\"route_to\":\"10055\",");
			message.append("\"auth_info\":"+RouterConstant.AUTH_INFO+",");
			message.append("\"other\":{}}");
			System.out.println(message.toString());
			ContentProducer cp = new ContentProducer()
			{				        
		        @Override
		        public void writeTo(OutputStream outstream) throws IOException
		        {
		            Writer writer = new OutputStreamWriter(outstream, "UTF-8");
			        writer.write(message.toString());
			        writer.flush();
			    }
			};
			method.setEntity(new EntityTemplate(cp));
			HttpResponse response = client.execute(method);
			Header[] herders = response.getAllHeaders();
			for(Header h : herders)
			{
				System.out.println(h.getName()+":"+h.getValue());
			}
			System.out.println(EntityUtils.toString(response.getEntity()));
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
