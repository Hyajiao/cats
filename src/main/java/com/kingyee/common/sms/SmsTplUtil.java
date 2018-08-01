package com.kingyee.common.sms;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kingyee.common.util.collection.ListUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 新短信通道,福建电信版
 * 增加可变签名的接口
 * @author 张宏亮
 * @author ph
 * @version 2015年6月30日下午1:25:43
 */
public class SmsTplUtil {

    /**
     * 日志对象
     */
    protected static Logger logger = LoggerFactory.getLogger(SmsTplUtil.class);

	/** 项目域名,短信标识 */
	private static final String DOMAIN = "cats.medlive.cn";
	/** 发送 url */
	private static final String SMS_SEND_URL = "http://service.callcenter.medlive.cn/apifj/smsbytemplet";

	/** httpclient超时时间10秒 */
	private static int HTTPCLIENT_TIMEOUT = 10000;

	/**
	 * 发送短信
	 * 
	 * @param mobile 手机号,只能是一个
	 * @param templateId 模板id 100001
	 * @param values 可变参数
	 * @return JsonObject
	 */
	public static JsonObject sendSms(String mobile, String templateId, List<String> values) {
		return sendSms(mobile, templateId, values, null);
	}

    /**
     * 发送短信-带可变签名
     *
     * @param mobile 手机号,只能是一个
     * @param templateId 模板id 100001
     * @param values 可变参数
     * @param sign 签名
     * @return JsonObject
     */
    public static JsonObject sendSms(String mobile, String templateId, List<String> values, String sign) {
        HttpClient client = null;
        HttpPost post = null;
        try {
            client = getHttpClient();
            post = new HttpPost(SMS_SEND_URL);
            post.setEntity(createSendEntity(mobile, templateId, values, sign));
            HttpResponse response = client.execute(post);
            if (response.getEntity() != null) {
                return new JsonParser().parse(EntityUtils.toString(response.getEntity())).getAsJsonObject();
            }
        } catch (Exception e) {
            String errMsg = mobile + "#" + templateId + "#" + ListUtil.listToStr(values, "||") + "#" + sign;
            logger.error("短信发送失败:" + errMsg, e);
        } finally {
            try {
                if (post != null) {
                    post.abort();
                }
                client.getConnectionManager().shutdown();
            } catch (Exception e) {
                // nothing to do
            }
        }
        JsonObject res = new JsonObject();
        res.addProperty("success", false);
        return res;
    }

	/**
	 * 发送
	 *
     * @param mobile 手机号,只能是一个
     * @param templateId 模板id 100001
     * @param values 可变参数
     * @param sign 签名
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static HttpEntity createSendEntity(String mobile, String templateId, List<String> values, String sign) throws UnsupportedEncodingException {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("domain", DOMAIN));
		parameters.add(new BasicNameValuePair("calleeNbr", mobile));
		parameters.add(new BasicNameValuePair("templetid", templateId));
		if(sign != null && !sign.equals("")){
            parameters.add(new BasicNameValuePair("Sign", sign));
        }

		for (int i = 0; i < values.size(); i++) {
			parameters.add(new BasicNameValuePair("value" + (i + 1), values.get(i)));
		}
		HttpEntity entity = new UrlEncodedFormEntity(parameters, "utf-8");
		return entity;
	}

	private static HttpClient getHttpClient() throws Exception {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		// 设置超时
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, HTTPCLIENT_TIMEOUT);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, HTTPCLIENT_TIMEOUT);
		// 为避免时间过长，不retry
		DefaultHttpRequestRetryHandler retryhandler = new DefaultHttpRequestRetryHandler(0, false);
		httpClient.setHttpRequestRetryHandler(retryhandler);

		HttpClientParams.setCookiePolicy(httpClient.getParams(), CookiePolicy.BROWSER_COMPATIBILITY);

		return httpClient;
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		//103833 	【Value1】您好，您【Value2】的志愿服务没有乘客，祝您一路顺风
		String id1 = "103833";
		List<String> values1 = new ArrayList<String>();
		values1.add("宋倩");
		values1.add("19:30");
		System.out.println(sendSms("18141906087", id1, values1));
		
		//103834 	【Value1】您好，您【Value2】预约的车辆信息为：【Value3】，祝您一路顺风。
		String id2 = "103834";
		List<String> values2 = new ArrayList<String>();
		values2.add("宋倩");
		values2.add("19:30");
		values2.add("红色，京N 58L01，同事：张宏亮 - 13811990734 ");
		System.out.println(sendSms("18141906087", id2, values2));
		
	}
}
