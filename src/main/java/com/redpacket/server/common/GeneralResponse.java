package com.redpacket.server.common;

public class GeneralResponse<T> {
	
	public static String ERROR = "error";
	public static String SUCCESS = "success";

	private String result;

	private T data;

	public GeneralResponse() {
	}

	public GeneralResponse(String result, T data) {
		this.result = result;
		this.data = data;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}