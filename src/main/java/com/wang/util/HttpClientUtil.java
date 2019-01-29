package com.wang.util;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {

	public static String doGet(String url, Map<String, String> params) {

		// 创建Httpclient对象
		CloseableHttpClient httpclient = HttpClients.createDefault();

		String resultString = "";
		CloseableHttpResponse response = null;
		try {
			// 创建uri
			URIBuilder builder = new URIBuilder(url);
			if (params != null) {
				for (String key : params.keySet()) {
					builder.addParameter(key, params.get(key));
				}
			}
			URI uri = builder.build();

			// 创建http GET请求
			HttpGet httpGet = new HttpGet(uri);
			if (params != null) {
				if(!StringUtils.isBlank(params.get("Token"))){
					httpGet.setHeader("HG_Server",params.get("Token"));
					params.remove("Token") ;
				}
				if(!StringUtils.isBlank(params.get("Device"))){
					httpGet.setHeader("HG_Device",params.get("Device"));
					params.remove("Device") ;
				}
			}

			// 执行请求
			response = httpclient.execute(httpGet);
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == 200) {
				resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return StringUtils.replaceBlank(resultString);
	}

	public static String doGet(String url) {
		return doGet(url, null);
	}

	public static String doPost(String url, Map<String, String> param) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			// 创建参数列表
			if (param != null) {
				List<NameValuePair> paramList = new ArrayList<NameValuePair>();
				for (String key : param.keySet()) {
					paramList.add(new BasicNameValuePair(key, param.get(key)));
				}
				// 模拟表单
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList,"utf-8");
				httpPost.setEntity(entity);
			}
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return StringUtils.replaceBlank(resultString);
	}

	public static String doPost(String url) {
		return doPost(url, null);
	}
	
	public static String doPostJson(String url, String json) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			// 创建请求内容
			StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
			httpPost.setEntity(entity);
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return resultString;
	}
	
	public static void main(String[] args) {
		String amount = "0.50";
		if(amount.compareTo("0.00")!=1)
		{
			System.out.println("异常");
		}
		System.out.println("信息："+doPost("https://blog.csdn.net/championhengyi/article/details/66583276"));
		/*String callBackUrl = "http://shopapi_v2.ums.test.viigoo.com:3315/api/Notify/YanZhiOrderPay";
		String orderNo = "32421224";
		String userId= "70395880";
		String sign = "orderNo=" + orderNo + "&userId=" + userId + "&couponAmount=199&cashAmount=";
		String rsaSign = RSASignature.sign(sign);
		System.out.println("rsaSign:" + rsaSign);
		Map<String, String> map = new HashMap<String, String>(7);
		map.put("orderNo", orderNo);
		map.put("userId", userId);
		map.put("cashAmount", "");
		map.put("couponAmount", "199");
		map.put("sign", rsaSign);
		map.put("code", "SUCCCESS");
		map.put("message", "付款成功");

		String data = JSONObject.toJSON(map).toString();
		System.out.println("参数data：" + data);

		Map<String, String> dataMap = new HashMap<>(1);
		dataMap.put("data", data);
		//dataMap.put("merchantNo", merchantNo);
		String returnMsg = HttpClientUtil.doPost(callBackUrl, dataMap);
		if(returnMsg.equals("\"FAILURE\"")){
            System.out.println("......");
        }
		System.out.println("接口返回信息：" + returnMsg);*/

		//Map returnMap = (Map) JSON.parse(returnMsg);

	}

}
