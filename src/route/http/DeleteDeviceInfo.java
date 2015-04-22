/*
 * 文 件 名:  DeleteDeviceInfo.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  pf_wangxiaowei
 * 修改时间:  2014年5月9日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package route.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  pf_wangxiaowei
 * @version  [版本号, 2014年5月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DeleteDeviceInfo
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
		String deviceId = "15105";
		HttpClient client = new DefaultHttpClient();
		HttpDelete method = new HttpDelete("http://10.189.24.66/devices/"+deviceId);
		try
		{
			method.setHeader("api-key", "RnCt7UNiutCOFWL4ImhjN5btG9I=");
			
			HttpResponse response = client.execute(method);
			
			System.out.println(EntityUtils.toString(response.getEntity()));
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

}
