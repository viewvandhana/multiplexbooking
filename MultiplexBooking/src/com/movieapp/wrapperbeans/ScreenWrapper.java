package com.movieapp.wrapperbeans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ScreenWrapper {

	private ScreenSeatWrapper screen;

	public ScreenSeatWrapper getScreen() {
		return screen;
	}

	public void setScreen(ScreenSeatWrapper screen) {
		this.screen = screen;
	}
	
}
