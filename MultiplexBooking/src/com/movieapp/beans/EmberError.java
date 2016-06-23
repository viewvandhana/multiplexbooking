package com.movieapp.beans;

public class EmberError {

	private int code;
	private String detail;
	
	public EmberError(int code,String msg)
	{
		this.code=code;
		this.detail=msg;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String message) {
		this.detail = message;
	}
	
	
}
