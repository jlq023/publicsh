package demo.bean;

import java.io.Serializable;
import java.util.Map;
 
 

public class NetworkBean implements Serializable {
	private int code;//网络请求 
	private String url;
	private String result;
	private Map<String, Object> params;
	private int timeOut = 30 * 1000;
	private String chatSet = "utf-8"; 
	private String filePath;//文件上传后，已将该路径重新修改,为服务器返回的路径
	private int fileType;
	private String errorMsg;
	private long id;
	private int mark; 
	//以下是测试微信，可以删除
	private int requestType;//0普通，1微信
	private String wxData;
	
	public String getWxData() {
		return wxData;
	}

	public void setWxData(String wxData) {
		this.wxData = wxData;
	}

	public int getRequestType() {
		return requestType;
	}

	public void setRequestType(int requestType) {
		this.requestType = requestType;
	}

	 

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public int getFileType() {
		return fileType;
	}

	public void setFileType(int fileType) {
		this.fileType = fileType;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	} 

	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	public String getChatSet() {
		return chatSet;
	}

	public void setChatSet(String chatSet) {
		this.chatSet = chatSet;
	}

	public int getCode() { 
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
}
