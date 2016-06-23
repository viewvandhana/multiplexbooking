package com.movieapp.daoimpl;

import java.util.ArrayList;
import java.util.Date;

import Exception.ResponseFailureException;

import com.adventnet.ds.query.Column;
import com.adventnet.ds.query.Criteria;
import com.adventnet.ds.query.QueryConstants;
import com.adventnet.ds.query.SelectQuery;
import com.adventnet.ds.query.SelectQueryImpl;
import com.adventnet.ds.query.Table;
import com.adventnet.ds.query.UpdateQuery;
import com.adventnet.ds.query.UpdateQueryImpl;
import com.adventnet.moviebooking.MOVIE;
import com.adventnet.moviebooking.MOVIESHOW;
import com.adventnet.persistence.DataAccessException;
import com.adventnet.persistence.Row;
import com.movieapp.beans.Movie;
import com.movieapp.interfaces.MickeyBaseDAO;
import com.movieapp.interfaces.RowAdapter;
import com.movieapp.util.AppUtil;

public class MovieDAOMickeyImpl extends MickeyBaseDAO<Movie> {

	RowAdapter<Movie> rowAdapter = new RowAdapter<Movie>() {

		@Override
		public Row asRow(Movie data) {
			// TODO Auto-generated method stub
			Row row = new Row(getTableName());
			row.set(MOVIE.MOVIE_NAME, data.getMovieName());
			row.set(MOVIE.GENRE, data.getGenre());
			row.set(MOVIE.CATEGORY, data.getCategory());
			row.set(MOVIE.CERTIFICATE, data.getCertificate());
			row.set(MOVIE.LANGUAGE, data.getLanguage());
			row.set(MOVIE.DURATION, data.getDuration());
			row.set(MOVIE.DESCRIPTION, data.getDescription());
			row.set(MOVIE.IMAGE_URL, data.getImageURL());
			row.set(MOVIE.DATE_RELEASED,
					AppUtil.Instance.getDateFromString(data.getReleasedDate()));
			return row;
		}

		@Override
		public Movie asBean(Row row) {
			// TODO Auto-generated method stub
			Movie movie = new Movie((Long) row.get(MOVIE.MOVIE_ID), (String)row.get(
					MOVIE.MOVIE_NAME), (String)row.get(MOVIE.GENRE), (String)row
					.get(MOVIE.CATEGORY), (String)row.get(MOVIE.CERTIFICATE),(String) row.get(MOVIE.LANGUAGE), (String)row.get(
							MOVIE.DURATION), (String)row.get(MOVIE.DESCRIPTION),
					(String)row.get(MOVIE.IMAGE_URL),(Date)row.get(MOVIE.DATE_RELEASED)!=null? AppUtil.Instance.getStringFromDate((Date)row.get(MOVIE.DATE_RELEASED)):null);
			return movie;
		}

	};

	@Override
	public RowAdapter<Movie> getAdapter() {

		return rowAdapter;

	}


	public Movie updateMovieById(Movie obj) throws DataAccessException,ResponseFailureException
	{
		UpdateQuery updateQuery = new UpdateQueryImpl(getTableName());
		Criteria criteria = new Criteria(Column.getColumn(getTableName(),
				getPrimaryKeyColumnName()), obj.getId(), QueryConstants.EQUAL);
		updateQuery.setCriteria(criteria);
		updateQuery.setUpdateColumn(MOVIE.MOVIE_NAME, obj.getMovieName());
		updateQuery.setUpdateColumn(MOVIE.GENRE, obj.getGenre());
		updateQuery.setUpdateColumn(MOVIE.CATEGORY, obj.getCategory());
		updateQuery.setUpdateColumn(MOVIE.CERTIFICATE,
				obj.getCertificate());
		updateQuery.setUpdateColumn(MOVIE.LANGUAGE, obj.getLanguage());
		updateQuery.setUpdateColumn(MOVIE.DURATION, obj.getDuration());
		updateQuery.setUpdateColumn(MOVIE.DESCRIPTION,
				obj.getDescription());
		updateQuery.setUpdateColumn(MOVIE.IMAGE_URL, obj.getImageURL());
		Date dateNew=AppUtil.Instance.getDateFromString(obj.getReleasedDate());
		updateQuery.setUpdateColumn(MOVIE.DATE_RELEASED,AppUtil.Instance.getStringFromDateForUpdation(dateNew));

		return update(updateQuery, criteria);
	
	}
	
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return MOVIE.TABLE;
	}

	
	@Override
	public String getPrimaryKeyColumnName() {
		// TODO Auto-generated method stub
		return MOVIE.MOVIE_ID;
	}

}
