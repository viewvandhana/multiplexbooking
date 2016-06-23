package com.movieapp.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.movieapp.beans.Screen;
import com.movieapp.beans.Seat;

public enum JSONParser {
    INSTANCE;

public Screen getScreenObject(String screen)
{
	try{
		
		JSONObject screenjObjString=new JSONObject(screen);
		JSONObject jObj=screenjObjString.optJSONObject("screen");
		
		Screen screenObj=new Screen();
		screenObj.setScreenName(jObj.optString("screenName"));
		screenObj.setId(jObj.optLong("id"));
		screenObj.setScreenRows(jObj.optInt("screenRows"));
		screenObj.setScreenColumns(jObj.optInt("screenColumns"));
		return screenObj;
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}

	return null;
}

public ArrayList<Seat> getSeatsArray(String screen)
{
	try{
		
		JSONObject jObj=new JSONObject(screen);
		JSONObject screenjObj=jObj.optJSONObject("screen");
		
		JSONArray seatArray=screenjObj.optJSONArray("seats");
		ArrayList<Seat> seatList=ObjectMapperUtil.getMapper().readValue(seatArray.toString(),TypeFactory.defaultInstance().constructCollectionType(List.class,  
				   Seat.class));
		return seatList;
		
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}

	return null;
}


}
