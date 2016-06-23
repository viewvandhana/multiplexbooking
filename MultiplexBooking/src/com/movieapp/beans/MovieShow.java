package com.movieapp.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class MovieShow {

	private Long id,screenID,movieID,showID;
	private String movieDate;
	private int availableSeats;

	public int getAvailableSeats() {
		return availableSeats;
	}
	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long movieShowId) {
		this.id = movieShowId;
	}
	public Long getScreenID() {
		return screenID;
	}
	
	public MovieShow() {
		super();
	}
	public MovieShow(Long movieShowId, Long screenId, Long movieId,
			Long showId, String movieShowDate) {
		super();
		this.id = movieShowId;
		this.screenID = screenId;
		this.movieID = movieId;
		this.showID = showId;
		this.movieDate = movieShowDate;
	}
	public void setScreenID(Long screenId) {
		this.screenID = screenId;
	}
	public Long getMovieID() {
		return movieID;
	}
	public void setMovieID(Long movieId) {
		this.movieID = movieId;
	}
	public Long getShowID() {
		return showID;
	}
	public void setShowID(Long showId) {
		this.showID = showId;
	}
	public String getMovieDate() {
		return movieDate;
	}
	public void setMovieDate(String movieShowDate) {
		this.movieDate = movieShowDate;
	}
	
}
