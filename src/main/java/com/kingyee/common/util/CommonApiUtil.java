package com.kingyee.common.util;

import com.google.gson.Gson;
import com.kingyee.common.util.encrypt.AesUtil;
import com.kingyee.common.util.http.HttpClientUtil;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommonApiUtil{
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(CommonApiUtil.class);


	/**
	 * 公共方法 HttpPost
	 * @param paramObj
	 * @param url
	 * @return
	 * @throws Exception
	 * @author zhangwenna
	 * @version 2017年5月25日下午5:35:29
	 * @param
	 */
	public static String callApi(Map<String,Object> paramObj, String url)throws Exception {
		HttpClient client = null;
		client = HttpClientUtil.getHttpClient();
		String result = null;

		HttpPost post = new HttpPost(url);
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		if(paramObj!=null){
			//AES加密
			String param = new Gson().toJson(paramObj);
			String encodeParam = AesUtil.encode(param, PropertyConst.DOMAIN_URL);
			parameters.add(new BasicNameValuePair("param", encodeParam));
		}
		parameters.add(new BasicNameValuePair("from", PropertyConst.DOMAIN_URL));
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, "UTF-8");
		post.setEntity(entity);
		HttpResponse response = client.execute(post);
		int statusCode = response.getStatusLine().getStatusCode();
		result = EntityUtils.toString(response.getEntity());
		if (statusCode == HttpStatus.SC_OK) {
			return result;
		}
		return null;
	}


	/**
	 * 公共方法 HttpGet
	 * @param
	 * @param url
	 * @return
	 * @throws Exception
	 * @author zhangwenna
	 * @version 2017年5月25日下午5:35:29
	 * @param
	 */
	public static String getUrl(String url){
		String result="";
		HttpClient client = null;
		HttpGet get = null;
		client = HttpClientUtil.getHttpClient();
		try{
			get = new HttpGet(url);
			HttpResponse response1 = client.execute(get);
			int statusCode = response1.getStatusLine().getStatusCode();
			// 1.3判断响应码
			// 1.4获取响应内容
			result = EntityUtils.toString(response1.getEntity());
			if (statusCode == HttpStatus.SC_OK) {
				return result;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("result="+result,e);
		}
		return null;
	}
}
