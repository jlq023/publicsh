package com.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import demo.bean.NetworkBean;
/**
 * httpclient  工具类
 * */
public class HttpClientManager {
	private static HttpClientManager httpClient = null;
	private final int SUCCESS = 0;
	private final int FAILURE = 1;
	private final int ERROR = 2;
	protected static final Logger log = Logger
			.getLogger(HttpClientManager.class.getName());

	public static HttpClientManager getInstance() {
		if (httpClient == null) {
			httpClient = new HttpClientManager();
		}
		return httpClient;
	}

	public synchronized void sendRequest(String url,
			Map<String, Object> params, NetworkBean networkBean) {
		if (params != null) {
			networkBean.setParams(params);
		}
		try {
			BasicHttpParams basicHttpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(basicHttpParams,
					networkBean.getTimeOut());
			HttpConnectionParams.setSoTimeout(basicHttpParams,
					networkBean.getTimeOut());
			DefaultHttpClient client = new DefaultHttpClient(basicHttpParams);
			HttpUriRequest postRequest = this.postRequest(url,
					networkBean.getParams(), networkBean);
			HttpResponse response = client.execute(postRequest);
			int requestResult = response.getStatusLine().getStatusCode();
			if (requestResult == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				Header ceheader = entity.getContentEncoding();
				if (ceheader != null) {
					for (HeaderElement element : ceheader.getElements()) {
						if ("gzip".equalsIgnoreCase(element.getName())) {
							entity = new GzipDecompressingEntity(
									response.getEntity());
							break;
						}
					}
				}
				networkBean.setCode(SUCCESS);
				networkBean.setResult(EntityUtils.toString(entity, "UTF-8"));
			} else {
				networkBean.setCode(FAILURE);
				networkBean.setResult("{\"ret\":1,\"retInfo\":\""
						+ requestResult + "\"}");
			}
		} catch (Exception e) {
			networkBean.setCode(ERROR);
			networkBean.setResult("HttpClientManager error:" + e.getMessage());
			e.printStackTrace();
		} finally {
			log.error("HttpClientManager finally " + networkBean.getUrl() + ","
					+ networkBean.getParams() + "\r\n"
					+ networkBean.getResult() + "\r\n"
					+ networkBean.getWxData());
		}
	}

	private HttpUriRequest postRequest(String url, Map<String, Object> params,
			NetworkBean networkBean) {
		HttpPost httppost = null;
		List<NameValuePair> listParams = new ArrayList<NameValuePair>();
		if (params != null) {
			for (String name : params.keySet()) {
				Object value = params.get(name);
				if (value != null) {
					listParams.add(new BasicNameValuePair(name, params
							.get(name).toString()));
				}
			}
		}
		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(listParams,
					HTTP.UTF_8);
			httppost = new HttpPost(url);
			httppost.addHeader("User-Agent",
					"Dalvik/1.6.0 (Linux; U; Android 4.2.1; 2013022 MIUI/JHACNBA13.0)");
			httppost.setHeader("Content-Type",
					"application/x-www-form-urlencoded; charset=utf-8");
			if (networkBean.getRequestType() == 0) {
				httppost.setEntity(entity);
			} else {
				httppost.setEntity(new StringEntity(networkBean.getWxData()));
			}
		} catch (UnsupportedEncodingException e) {
			e.getMessage();
		}
		return httppost;
	}

	// public synchronized NetworkBean sendRequest(String url, Object key[],
	// Object value[])
	// throws HttpException, IOException {
	// String charSet="utf-8";
	// NetworkBean result = new NetworkBean();
	// HttpClient httpClient = new HttpClient();
	// PostMethod postMethod = new PostMethod(url);
	// NameValuePair[] data = new NameValuePair[] {};
	// List<NameValuePair> paramList = this.setParams(key, value);
	// postMethod.setRequestHeader("Content-Type",
	// "application/x-www-form-urlencoded;charset="+charSet);
	// postMethod.setRequestBody(paramList.toArray(data));
	// int resultCode = httpClient.executeMethod(postMethod);
	// if (resultCode != HttpStatus.SC_OK) {
	// result.setCode(1);
	// result.setResult(""+resultCode);
	//
	// } else {
	// String message = postMethod.getResponseBodyAsString();
	// // message = new String(message.toString().getBytes("iso-8859-1"),
	// // "utf-8");
	// result.setCode(0);
	// result.setResult(message);
	// }
	// return result;
	// }
	//
	// private List<NameValuePair> setParams(Object key[], Object value[]) {
	// List<NameValuePair> resultList = new ArrayList<NameValuePair>();
	// NameValuePair param = null;
	// if (key.length > 0 && key.length == value.length) {
	// for (int i = 0; i < key.length; i++) {
	// param = new NameValuePair(key[i].toString(), value[i]
	// .toString());
	// resultList.add(param);
	// }
	// }
	// return resultList;
	// }
}
