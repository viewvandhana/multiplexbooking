package com.movieapp.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Movie {

	private Long id;
	private String movieName;
	private String genre;
	private String category;
	private String certificate;
	private String language;
	private String duration;
	private String description;
	private String imageURL;
	private String releasedDate;
	
	public Movie()
	{
	  super();	
	}
	
	
	public Movie(Long movieId,String movieName,String genre,String category,String certificate,String language,String duration,String description,String imageUrl,String releasedDate)
	{
		
		this.movieName=movieName;
		this.id=movieId;
		this.genre=genre;
		this.category=category;
		this.certificate=certificate;
		this.language=language;
		this.duration=duration;
		this.description=description;
		this.imageURL=imageUrl;
		this.releasedDate=releasedDate;
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCertificate() {
		return certificate;
	}
	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageUrl) {
		this.imageURL = imageUrl;
	}
	public String getReleasedDate() {
		return releasedDate;
	}
	public void setReleasedDate(String releasedDate) {
		this.releasedDate = releasedDate;
	}
	
	
}
