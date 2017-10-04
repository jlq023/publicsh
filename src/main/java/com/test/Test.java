package com.test;

import java.util.HashMap;
import java.util.Map;

import com.util.HttpClientManager;
import com.util.InterfaceUtil;

import demo.bean.NetworkBean;

public class Test {
	public static void main(String[] args) {
		InterfaceUtil util = new InterfaceUtil();
		Map<String, Map<String, Object>> params = new HashMap<String, Map<String, Object>>();
		Map<String, String> methodMap = new HashMap<String, String>();
		String method = "query,save";
		String methodArr[] = method.split(",");
		for (String m : methodArr) {
			methodMap.put(m, m);
		}
		util.getServiceParams("/user", "com.controller.UserController", params,
				methodMap);
		util.printWord(params, "d:/word.docx", "/user");
//		new Test().query();
	}

	 
	public void query() {
		Map<String, Object> params = new HashMap<String, Object>();
		NetworkBean networkBean = new NetworkBean();
		int index = 1;
		params.put("status", index);
		index++;
		HttpClientManager.getInstance().sendRequest(
				"http://127.0.0.1:8080/publicsh//user/query.htm", params,
				networkBean);
		System.out.println(networkBean.getCode() + ","
				+ networkBean.getResult());
	}

	public void save() {
		Map<String, Object> params = new HashMap<String, Object>();
		NetworkBean networkBean = new NetworkBean();
		int index = 1;
		params.put("name", index);
		index++;
		params.put("passWord", index);
		index++;
		params.put("paramsStatus", index);
		index++;
		params.put("age", index);
		index++;
		HttpClientManager.getInstance().sendRequest(
				"http://127.0.0.1:8080/publicsh//user/save.htm", params,
				networkBean);
		System.out.println(networkBean.getCode() + ","
				+ networkBean.getResult());
	}
}
