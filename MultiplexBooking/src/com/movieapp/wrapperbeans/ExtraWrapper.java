package com.movieapp.wrapperbeans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.movieapp.beans.Extra;

@JsonInclude(Include.NON_NULL)
public class ExtraWrapper {

	private Extra extra;

	public Extra getExtra() {
		return extra;
	}

	public void setExtra(Extra extra) {
		this.extra = extra;
	}
	
	
	
}
