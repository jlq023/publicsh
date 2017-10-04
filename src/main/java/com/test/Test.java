package com.test;

import java.util.HashMap;
import java.util.Map;
 
import com.util.InterfaceUtil;

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
		util.printWord(params, "d:/word.docx","/user");
	}

	public void tset() {

	}

}
