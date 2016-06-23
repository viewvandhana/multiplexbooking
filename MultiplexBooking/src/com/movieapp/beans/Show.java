package com.movieapp.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)


public class Show {

	
	  private Long id;
	  private String showName;
	  private String startTime;
	  private String endTime;
	  
	public Show()
	{
		super();
	}
	  
	public Show(Long id,String showName,String startTime,String endTime)
	{
		this.showName=showName;
		this.startTime=startTime;
		this.endTime=endTime;
		this.id=id;
	}
	  
    public Long getId() {
		return id;
	}
	public void setId(Long showId) {
		this.id = showId;
	}
	public String getShowName() {
		return showName;
	}
	public void setShowName(String showName) {
		this.showName = showName;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}


}
