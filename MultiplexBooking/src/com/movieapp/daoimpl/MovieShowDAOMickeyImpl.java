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
import com.adventnet.persistence.DataObject;
import com.adventnet.persistence.Row;
import com.movieapp.beans.MovieShow;
import com.movieapp.interfaces.MickeyBaseDAO;
import com.movieapp.interfaces.RowAdapter;
import com.movieapp.util.AppUtil;
import com.movieapp.wrapperbeans.MovieShowResponseWrapper;

public class MovieShowDAOMickeyImpl extends MickeyBaseDAO<MovieShow> {

	RowAdapter<MovieShow> rowAdapter = new RowAdapter<MovieShow>() {

		@Override
		public Row asRow(MovieShow data) {
			// TODO Auto-generated method stub
			Row row = new Row(getTableName());
			row.set(MOVIESHOW.SCREEN_ID, data.getScreenID());
			row.set(MOVIESHOW.SHOW_ID, data.getShowID());
			row.set(MOVIESHOW.MOVIE_ID, data.getMovieID());
			row.set(MOVIESHOW.DATE,
					AppUtil.Instance.getDateFromString(data.getMovieDate()));
			return row;
		}

		@Override
		public MovieShow asBean(Row row) {
			// TODO Auto-generated method stub
			MovieShow movieShow = new MovieShow(
					(Long) row.get(MOVIESHOW.MOVIE_SHOW_ID),
					(Long) row.get(MOVIESHOW.SCREEN_ID), (Long) row.get(MOVIESHOW.MOVIE_ID),
					(Long) row.get(MOVIESHOW.SHOW_ID), (Date)row.get(MOVIESHOW.DATE)!=null?AppUtil.Instance.getStringFromDate((Date)row.get(MOVIESHOW.DATE)):null);
			return movieShow;
		}

	};

	@Override
	public RowAdapter<MovieShow> getAdapter() {

		return rowAdapter;

	}

	
  public MovieShow updateMovieShowById(MovieShow obj) throws DataAccessException,ResponseFailureException
  {
	  UpdateQuery updateQuery = new UpdateQueryImpl(getTableName());
	  Criteria criteria = new Criteria(Column.getColumn(getTableName(),
				getPrimaryKeyColumnName()),  obj.getId(),
				QueryConstants.EQUAL);
		updateQuery.setCriteria(criteria);
		updateQuery.setUpdateColumn(MOVIESHOW.SCREEN_ID,
				obj.getScreenID());
		updateQuery.setUpdateColumn(MOVIESHOW.SHOW_ID,  obj.getShowID());
		Date dateNew=AppUtil.Instance.getDateFromString(obj.getMovieDate());
		updateQuery.setUpdateColumn(MOVIESHOW.DATE,AppUtil.Instance.getStringFromDateForUpdation(dateNew));

		return update(updateQuery, criteria);
  }
	
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return MOVIESHOW.TABLE;
	}

	@Override
	public String getPrimaryKeyColumnName() {
		// TODO Auto-generated method stub
		return MOVIESHOW.MOVIE_SHOW_ID;
	}

	

	public Long getScreenIdForMovieShowId(Long msId) {
		// TODO Auto-generated method stub
		try {
			SelectQuery movieShowScreenQuery = new SelectQueryImpl(
					Table.getTable(MOVIESHOW.TABLE));
			movieShowScreenQuery.addSelectColumn(new Column(getTableName(),
					MOVIESHOW.SCREEN_ID));
			movieShowScreenQuery.addSelectColumn(new Column(getTableName(),
					MOVIESHOW.MOVIE_SHOW_ID));
			Criteria criteria = new Criteria(Column.getColumn(getTableName(),
					MOVIESHOW.MOVIE_SHOW_ID), msId, QueryConstants.EQUAL);
			movieShowScreenQuery.setCriteria(criteria);
			DataObject dataObject = getDataObject(movieShowScreenQuery);
			Row row = dataObject.getFirstRow(getTableName());

			return (Long) row.get(MOVIESHOW.SCREEN_ID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Long isMovieShowScheduledForScreen(Long screenId) throws ResponseFailureException {
		SelectQuery selectQuery = new SelectQueryImpl(
				Table.getTable(getTableName()));
		Criteria criteria = new Criteria(
				new Column(getTableName(), MOVIESHOW.SCREEN_ID), screenId,
				QueryConstants.EQUAL);
		   selectQuery.addSelectColumn(new Column(getTableName(),getPrimaryKeyColumnName()));
			
		selectQuery.setCriteria(criteria);
		ArrayList<MovieShow> movieShowList = getRows(selectQuery);
		if (movieShowList.size() > 0) {
			return movieShowList.get(0).getId();

		}
		return null;

	}
	
	public MovieShowResponseWrapper getMovieShowsByMovieShowIdList(ArrayList<Long> msIdList) throws ResponseFailureException {

		SelectQuery moviesQuery = new SelectQueryImpl(
				Table.getTable(MOVIESHOW.TABLE));
	   Criteria criteria=new Criteria(new Column(MOVIESHOW.TABLE,MOVIESHOW.MOVIE_SHOW_ID),msIdList.toArray(),QueryConstants.IN);
	   moviesQuery.setCriteria(criteria);

		return JoinDAO.getMovieShowsOnJoinCriteria(JoinDAO.addMovieShowSelectColsAndJoins(moviesQuery));

	}

	public MovieShowResponseWrapper getMovieShowById(Long msId) throws ResponseFailureException {

		return JoinDAO.getMovieShowsOnJoinCriteria(JoinDAO.constructMovieshowSelectQueryOnJoin(msId));

	}
	public MovieShowResponseWrapper getMovieShowsByFilters(String dateInString, Long movieId,
			Long screenId) throws ResponseFailureException {

		SelectQuery moviesQuery = new SelectQueryImpl(
				Table.getTable(MOVIESHOW.TABLE));
		Criteria criteria = null;
		if (dateInString != null) {
			criteria = new Criteria(Column.getColumn(MOVIESHOW.TABLE, MOVIESHOW.DATE),
					AppUtil.Instance.getDateFromString(dateInString),
					QueryConstants.EQUAL);

		}
		if (movieId != null) {

			if (criteria == null) {
				criteria = new Criteria(Column.getColumn(MOVIESHOW.TABLE,
						MOVIESHOW.MOVIE_ID), movieId, QueryConstants.EQUAL);
			} else {
				Criteria newCriteria = new Criteria(Column.getColumn(
						MOVIESHOW.TABLE, MOVIESHOW.MOVIE_ID), movieId, QueryConstants.EQUAL);

				criteria = criteria.and(newCriteria);
			}
		}
		if (screenId != null) {

			if (criteria == null) {
				criteria = new Criteria(Column.getColumn(MOVIESHOW.TABLE,
						MOVIESHOW.SCREEN_ID), screenId, QueryConstants.EQUAL);
			} else {
				criteria = criteria.and(new Criteria(Column.getColumn(
						MOVIESHOW.TABLE, MOVIESHOW.SCREEN_ID), screenId,
						QueryConstants.EQUAL));
			}

		}
		/*if (msId != null) {
			if (criteria == null) {
				criteria = new Criteria(Column.getColumn(MOVIESHOW.TABLE,
						MOVIESHOW.MOVIE_SHOW_ID), msId, QueryConstants.EQUAL);
			} else {
				criteria = criteria.and(new Criteria(Column.getColumn(
						MOVIESHOW.TABLE, MOVIESHOW.MOVIE_SHOW_ID), msId,
						QueryConstants.EQUAL));
			}

		}*/
		moviesQuery.setCriteria(criteria);

		return JoinDAO.getMovieShowsOnJoinCriteria(JoinDAO.addMovieShowSelectColsAndJoins(moviesQuery));

	}
	
	public int checkIfMovieShowExist(Long screenId,Long showId,String date) throws ResponseFailureException
	{
		
		Date dateNew=AppUtil.Instance.getDateFromString(date);
		SelectQuery selectQuery = new SelectQueryImpl(Table.getTable(getTableName())); 
		Criteria criteria=new Criteria(new Column(getTableName(),MOVIESHOW.SCREEN_ID),screenId,QueryConstants.EQUAL);
		criteria=criteria.and(new Criteria(new Column(getTableName(),MOVIESHOW.SHOW_ID),showId,QueryConstants.EQUAL));
		criteria=criteria.and(new Criteria(new Column(getTableName(),MOVIESHOW.DATE),dateNew,QueryConstants.EQUAL));
		selectQuery.addSelectColumn(new Column(null,"*"));
		selectQuery.setCriteria(criteria);
        ArrayList<MovieShow> movieShowList=getRows(selectQuery);
	   	return movieShowList.size();
		//return 0;
	   
	}
}
