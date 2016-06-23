package com.movieapp.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtil {

	static ObjectMapper mapper=new ObjectMapper();
	
	public static String getCustomMappedString(String rootName,Object obj)
	{
		   try {
		    	String str= mapper.writer().withRootName(rootName).writeValueAsString(obj);
			    return str;
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   return null;

		
	}
	
	public static ObjectMapper getMapper()
	{
		   return mapper;

		
	}
}
