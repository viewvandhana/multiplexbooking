package com.movieapp.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Seat {

	private Long id;
	private Long screenID;
	private Long categoryID;
	private String name;
	private int rowNumber;
	private int columnNumber;
	private boolean status;
	private boolean isDeleted;
	
	public boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getScreenID() {
		return screenID;
	}

	public void setScreenID(Long screenID) {
		this.screenID = screenID;
	}

	public Long getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(Long categoryID) {
		this.categoryID = categoryID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	public int getColumnNumber() {
		return columnNumber;
	}

	public void setColumnNumber(int columnNumber) {
		this.columnNumber = columnNumber;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	
	public Seat()
	{
		super();
	}
	
	public Seat(Long id, Long screenId, Long categoryId, String name,
			int rowNo, int colNo, boolean status) {
		super();
		this.id = id;
		this.screenID = screenId;
		this.categoryID = categoryId;
		this.name = name;
		this.rowNumber = rowNo;
		this.columnNumber = colNo;
		this.status = status;
	}
	
	
	}
