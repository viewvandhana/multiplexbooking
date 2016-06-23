package com.movieapp.rest.APIResponse;

import org.json.JSONObject;


public class ObjectResponse extends APIResponse {

	
	private String response;

	public String getResponse() {
		return response;
	}

	public void setResponse(String resp) {
		this.response = resp;
	}
	
	public String constructJson()
	{
		
		String resp="{code :"+getCode()+","+response+"}";
		return resp;
		
	}
	
}
