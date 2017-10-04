package com.util;

import java.awt.Color;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.rtf.RtfWriter2;
 
public class InterfaceUtil {

	 
	 
	
	/**
	 * @param params 调用接口需要的参数
	 * @param url   文件输出地址
	 * @param serviceName 控制层@RequestMapping注解对应的值
	 */
	public void printWord(Map<String, Map<String, Object>> params, String url,String serviceName) {
		Document document = new Document(PageSize.A4);
		try {
			RtfWriter2.getInstance(document, new FileOutputStream(url));
			document.open();
			Paragraph ph = new Paragraph();
			Font f = new Font();
			Paragraph p = new Paragraph("接口文档", new Font(Font.NORMAL, 18,
					Font.BOLDITALIC, new Color(0, 0, 0)));
			p.setAlignment(1);
			document.add(p);
			ph.setFont(f);
			Iterator iterator = params.entrySet().iterator();
			String method = "";
			int paramsStatus = 1;
			while (iterator.hasNext()) {
				Map.Entry entry = (Entry) iterator.next();
				method = entry.getKey().toString();
				Map<String, Object> methodParamsMap = (Map<String, Object>) entry
						.getValue();
				Iterator pIterator = methodParamsMap.entrySet().iterator();
				Table table = new Table(4);
				document.add(new Paragraph(""));
				document.add(new Paragraph("接口名称：xxxx   调用路径:("+serviceName+"/" + method + ".json)"));
				table.setBorderWidth(1);
				table.setBorderColor(Color.red);
				table.setPadding(0);
				table.setSpacing(0);
				table.setBackgroundColor(Color.red);
				table.addCell(new Paragraph("参数名称"));
				table.addCell(new Paragraph("类型"));
				table.addCell(new Paragraph("是否必填"));
				table.addCell(new Paragraph("备注"));
				String valueType = "";
				String key = "";
				while (pIterator.hasNext()) {
					Map.Entry pEntry = (Entry) pIterator.next();
					valueType = pEntry.getValue().toString();
					key = pEntry.getKey().toString();
					if ("java.lang.String".equals(valueType)) {
						valueType = "String";
					}
					if(key.equals("paramsStatus")){
						paramsStatus=0;
						continue;
					}
					table.addCell(new Paragraph("  "
							+ key));
					table.addCell(new Paragraph("  "+valueType));
//							+ pEntry.getValue().toString()));
					if(paramsStatus==1){
						table.addCell(new Paragraph("y"));
					}else{
						table.addCell(new Paragraph("n"));
					}
					table.addCell(new Paragraph("remarks"));
				}

				document.add(table);
				document.add(new Paragraph(""));
				Table outTable = new Table(5);
				document.add(new Paragraph("输出参数"));
				outTable.setBorderWidth(1);
//				outTable.setBorderColor(Color.red);
				outTable.setPadding(0);
				outTable.setSpacing(0);
				outTable.setBackgroundColor(Color.red);
				outTable.addCell(new Paragraph("标签"));
				outTable.addCell(new Paragraph("参数名称"));
				outTable.addCell(new Paragraph("参数类型"));
				outTable.addCell(new Paragraph("是否必填"));
				outTable.addCell(new Paragraph("备注"));
				outTable.addCell(new Paragraph("tag"));
				outTable.addCell(new Paragraph("ret"));
				outTable.addCell(new Paragraph("int"));
				outTable.addCell(new Paragraph("y"));
				outTable.addCell(new Paragraph("0成功，1失败"));
				outTable.addCell(new Paragraph("retInfo"));
				outTable.addCell(new Paragraph("int"));
				outTable.addCell(new Paragraph("必填"));
				outTable.addCell(new Paragraph("对接口的描述"));
				document.add(outTable);
			}
			document.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 得到接口的参数
	 * @param service 
	 * @param className  controller类的全路径，如：com.controller.UserController
	 * @param params 存放参数的map,只需要初始化即可
	 * @param methodMap controller的方法名称，通过这个map来生成接口文档。如：save,query等，具体参见测试方法
	 */
	public void getServiceParams(String service, String className,
			Map<String, Map<String, Object>> params,
			Map<String, String> methodMap) {
		StringBuilder sb = new StringBuilder();
		Map<String, StringBuilder> serviceTestMap = new HashMap<String, StringBuilder>();
		Map<String, Object> methodParams = new LinkedHashMap<String, Object>();
		int index = 0;
		try {
			Class clazz = Class.forName(className);
			ClassPool pool = ClassPool.getDefault();
			List paramsType = new ArrayList();
			CtClass cc = pool.get(clazz.getName());
			Method method[] = clazz.getDeclaredMethods();
			for (Method m : method) {
				String methodName = m.getName();
				if (!methodMap.containsKey(methodName)) {
					continue;
				}
				paramsType = new ArrayList();
				Class[] cParams = m.getParameterTypes();
				for (Class cType : cParams) {
					//此处的包是，我们系统封装后的包名，你可以注释这些代码
					if (cType.getName().equals("im.core.mvc.controller.IModel")
							|| cType.getName().equals("im.core.dao.Dao")|| cType.getName().equals("lj.sys.UserToken")) {
						continue;
					}
					paramsType.add(cType.getName());
				}
				index = 0;
				CtMethod cm = cc.getDeclaredMethod(methodName);
				MethodInfo methodInfo = cm.getMethodInfo();
				CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
				LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute
						.getAttribute(LocalVariableAttribute.tag);
				if (attr == null) {
					// exception
				}
				String[] paramNames = new String[cm.getParameterTypes().length];
				int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
				for (int i = 0; i < paramNames.length; i++)
					paramNames[i] = attr.variableName(i + pos);

				for (int i = 0; i < paramNames.length; i++) {
					String paramName = paramNames[i];
					if (paramName.equals("model") || paramName.equals("dao")|| paramName.equals("userToken")) {
						if(paramName.equals("dao")){
							methodParams.put("paramsStatus",1);
						}
						continue;
					}
					methodParams.put(paramNames[i], paramsType.get(index)
							.toString());
					if (serviceTestMap.containsKey(methodName)) {
						serviceTestMap.get(methodName).append(
								paramNames[i] + ",");
					} else {
						sb = new StringBuilder();
						sb.append(paramNames[i] + ",");
						serviceTestMap.put(methodName, sb);
					}
					index++; 
				}
				params.put(methodName, methodParams);
				methodParams = new LinkedHashMap<String, Object>();
			}
			Iterator iterator = serviceTestMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry entry = (Entry) iterator.next();
				createRequest(service, entry.getValue().toString(), entry
						.getKey().toString(), 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 创建测试方法
	 * @param service
	 * @param msg 参数key
	 * @param method 方法名称
	 * @param type  
	 * @return
	 */
	public StringBuffer createRequest(String service, String msg,
			String method, int type) {
		StringBuffer sb = new StringBuffer("public void " + method + "(){")
				.append("\r\n");
		sb.append("Map<String, Object> params = new HashMap<String, Object>();");
		sb.append("\r\n");
		sb.append("NetworkBean networkBean = new NetworkBean();	");
		sb.append("\r\n");
		sb.append("int index=1;"); 
		String msgArr[] = msg.split(",");
		for (String m : msgArr) {
			sb.append("params.put(\"" + m + "\", index);");
			sb.append("\r\n");
			sb.append("index++;");
			sb.append("\r\n");
		} 
			sb.append(
					"HttpClientManager.getInstance().sendRequest(\"" + URL
							+ service + "/" + method
							+ ".htm\",params, networkBean);").append("\r\n"); 
		sb.append("System.out.println(networkBean.getCode()+\",\"+networkBean.getResult()); ");
		sb.append("}");
		log.info(sb.toString());
		return sb;
	}
	private static final String URL = "http://127.0.0.1:8080/publicsh/";
	private static final Logger log = Logger.getLogger(InterfaceUtil.class);
}
