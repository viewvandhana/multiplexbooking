package com.movieapp.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Customer {

	private Long id;
	private String name;
	private String email;
	private String phoneNumber;
	
	public Customer()
	{
		super();
	}
	
	public Customer(Long id, String customerName, String customerEmail,
			String customerPhone) {
		super();
		this.id = id;
		this.name = customerName;
		this.email = customerEmail;
		this.phoneNumber = customerPhone;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String customerName) {
		this.name = customerName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String customerEmail) {
		this.email = customerEmail;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String customerPhone) {
		this.phoneNumber = customerPhone;
	}
	
}
