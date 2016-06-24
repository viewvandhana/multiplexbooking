package com.movieapp.beans;

//test commit comment
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Category {

	private Long id;
	private String categoryName;
	private Float fare;

	public Category()
	{
		super();
	}
	
	public Category(Long id, String categoryName, Float categoryFare) {
		super();
		this.id = id;
		this.categoryName = categoryName;
		this.fare = categoryFare;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Float getFare() {
		return fare;
	}
	public void setFare(Float categoryFare) {
		this.fare = categoryFare;
	}
	
	

}
