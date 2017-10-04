package com.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import demo.bean.Manageruser;

@Controller
@RequestMapping("/user")
public class UserController {

	@ResponseBody
	@RequestMapping("save")
	public String save(String name, String passWord,String paramsStatus, int age) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("code", 0);
		jsonObj.put("passWord", passWord);
		jsonObj.put("name", name);
		return jsonObj.toJSONString();
	}

	@ResponseBody
	@RequestMapping("query")
	public String query(short status) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("code", 0);
		// Manageruser 一个普通java对像
		List<Manageruser> data = new ArrayList<Manageruser>();
		for (int i = 0; i < 5; i++) {
			Manageruser m = new Manageruser();
			m.setMid(100L);
			m.setMloginId("testLoginId" + i);
			m.setMstatus(status);
			data.add(m);
		}
		jsonObj.put("data", data);
		return jsonObj.toJSONString();
	}

	@RequestMapping("/index")
	public ModelAndView index(String validate, HttpServletRequest request) {
		System.out.print("CreateCodeController index:" + validate);
		ModelAndView result = new ModelAndView();
		result.addObject("mValue", "this is 大家好");
		result.setViewName("/code/index");
		return result;
	}

}
