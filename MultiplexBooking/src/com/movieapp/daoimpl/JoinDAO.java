package com.movieapp.daoimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import Exception.ResponseFailureException;

import com.adventnet.ds.query.Column;
import com.adventnet.ds.query.Criteria;
import com.adventnet.ds.query.Join;
import com.adventnet.ds.query.QueryConstants;
import com.adventnet.ds.query.SelectQuery;
import com.adventnet.ds.query.SelectQueryImpl;
import com.adventnet.ds.query.Table;
import com.adventnet.moviebooking.CATEGORY;
import com.adventnet.moviebooking.CUSTOMER;
import com.adventnet.moviebooking.EXTRA;
import com.adventnet.moviebooking.MOVIE;
import com.adventnet.moviebooking.MOVIESHOW;
import com.adventnet.moviebooking.SCREEN;
import com.adventnet.moviebooking.SEAT;
import com.adventnet.moviebooking.SHOWDETAIL;
import com.adventnet.moviebooking.SHOWSEAT;
import com.adventnet.moviebooking.TICKET;
import com.adventnet.moviebooking.TICKETCHARGE;
import com.adventnet.persistence.DataObject;
import com.adventnet.persistence.Row;
import com.movieapp.beans.Category;
import com.movieapp.beans.Movie;
import com.movieapp.beans.MovieShow;
import com.movieapp.beans.Screen;
import com.movieapp.beans.Seat;
import com.movieapp.beans.Show;
import com.movieapp.beans.Ticket;
import com.movieapp.beans.TicketCharge;
import com.movieapp.daofactory.CategoryDAOFactory;
import com.movieapp.daofactory.CustomerDAOFactory;
import com.movieapp.daofactory.ExtraDAOFactory;
import com.movieapp.daofactory.MovieDAOFactoy;
import com.movieapp.daofactory.MovieShowDAOFactory;
import com.movieapp.daofactory.ScreenDAOFactory;
import com.movieapp.daofactory.SeatDAOFactory;
import com.movieapp.daofactory.ShowDAOFactory;
import com.movieapp.daofactory.ShowSeatDAOFactory;
import com.movieapp.daofactory.TicketChargeDAOFactory;
import com.movieapp.daofactory.TicketDAOFactory;
import com.movieapp.util.ObjectMapperUtil;
import com.movieapp.wrapperbeans.MovieShowResponseWrapper;
import com.movieapp.wrapperbeans.MovieShowSeatResponseWrapper;
import com.movieapp.wrapperbeans.ScreenSeatResponseJSONWrapper;
import com.movieapp.wrapperbeans.ScreenSeatResponseWrapper;
import com.movieapp.wrapperbeans.SeatResponseWrapper;
import com.movieapp.wrapperbeans.TicketResponseJSONWrapper;
import com.movieapp.wrapperbeans.TicketResponseWrapper;

public class JoinDAO {

	public static MovieShowResponseWrapper getMovieShowsOnJoinCriteria(
			SelectQuery moviesQuery) throws ResponseFailureException {

		try {

			DataObject dataObject = MovieDAOFactoy
					.getMovieDAOInstance("mickey").getDataObject(moviesQuery);

			ArrayList<MovieShow> movieShows = new ArrayList<MovieShow>();

			Iterator rows = dataObject.getRows(MOVIESHOW.TABLE);
			while (rows.hasNext()) {
				Row row = (Row) rows.next();
				MovieShow movieShow = (MovieShow) MovieShowDAOFactory
						.getMovieShowDAOInstance("mickey").getAdapter()
						.asBean(row);
				movieShow
						.setAvailableSeats(((ShowSeatDAOMickeyImpl) ShowSeatDAOFactory
								.getShowSeatDAOInstance("mickey"))
								.getAvailableSeatForMovieShow(movieShow.getId()));
				movieShows.add(movieShow);

			}

			MovieShowResponseWrapper movieShowResponseWrapper = new MovieShowResponseWrapper();
			if (movieShows.size() == 1) {
				movieShowResponseWrapper.setMovieshow(movieShows.get(0));

			} else {
				movieShowResponseWrapper.setMovieshows(movieShows);

			}
			movieShowResponseWrapper.setScreens(ScreenDAOFactory
					.getScreenDAOInstance("mickey").getBeans(
							dataObject.getRows(SCREEN.TABLE)));
			movieShowResponseWrapper.setShows(ShowDAOFactory
					.getShowDAOInstance("mickey").getBeans(
							dataObject.getRows(SHOWDETAIL.TABLE)));
			movieShowResponseWrapper.setMovies(MovieDAOFactoy
					.getMovieDAOInstance("mickey").getBeans(
							dataObject.getRows(MOVIE.TABLE)));

			return movieShowResponseWrapper;
		} catch (Exception e) {
			throw new ResponseFailureException(e.getMessage());
		}

	}

	public static SelectQuery constructMovieshowSelectQueryOnJoin(Long msId) {
		SelectQuery moviesQuery = new SelectQueryImpl(
				Table.getTable(MOVIESHOW.TABLE));
		Criteria criteria = new Criteria(Column.getColumn(MOVIESHOW.TABLE,
				MOVIESHOW.MOVIE_SHOW_ID), msId, QueryConstants.EQUAL);

		moviesQuery.setCriteria(criteria);
		return addMovieShowSelectColsAndJoins(moviesQuery);
	}

	public static SelectQuery addMovieShowSelectColsAndJoins(
			SelectQuery moviesQuery) {
		moviesQuery.addSelectColumn(new Column(MOVIE.TABLE, "*"));
		moviesQuery.addSelectColumn(new Column(MOVIESHOW.TABLE, "*"));
		moviesQuery.addSelectColumn(new Column(SHOWDETAIL.TABLE, "*"));
		moviesQuery.addSelectColumn(new Column(SCREEN.TABLE, "*"));

		Join movieJoin = new Join(MOVIESHOW.TABLE, MOVIE.TABLE,
				new String[] { MOVIESHOW.MOVIE_ID },
				new String[] { MOVIE.MOVIE_ID }, Join.INNER_JOIN);
		Join screenJoin = new Join(MOVIESHOW.TABLE, SCREEN.TABLE,
				new String[] { MOVIESHOW.SCREEN_ID },
				new String[] { SCREEN.SCREEN_ID }, Join.INNER_JOIN);
		Join showJoin = new Join(MOVIESHOW.TABLE, SHOWDETAIL.TABLE,
				new String[] { MOVIESHOW.SHOW_ID },
				new String[] { SHOWDETAIL.SHOW_ID }, Join.INNER_JOIN);

		moviesQuery.addJoin(movieJoin);
		moviesQuery.addJoin(screenJoin);
		moviesQuery.addJoin(showJoin);

		return moviesQuery;

	}

	public static String getBookTicketMessage(Long msId)
			throws ResponseFailureException {

		try {

			DataObject dataObject = MovieShowDAOFactory
					.getMovieShowDAOInstance("mickey").getDataObject(
							constructMovieshowSelectQueryOnJoin(msId));

			Screen screen = (Screen) ScreenDAOFactory
					.getScreenDAOInstance("mickey")
					.getBeans(dataObject.getRows(SCREEN.TABLE)).get(0);
			Movie movie = (Movie) MovieDAOFactoy.getMovieDAOInstance("mickey")
					.getBeans(dataObject.getRows(MOVIE.TABLE)).get(0);
			Show show = (Show) ShowDAOFactory.getShowDAOInstance("mickey")
					.getBeans(dataObject.getRows(SHOWDETAIL.TABLE)).get(0);

			String bookTicketMessage = "Movie:" + movie.getMovieName() + "\n"
					+ "Screen:" + screen.getScreenName() + "\n" + "Show:"
					+ show.getShowName() + " " + show.getStartTime();
			return bookTicketMessage;
		} catch (Exception e) {
			throw new ResponseFailureException(e.getMessage());
		}

	}

	public static ScreenSeatResponseWrapper getScreensOnJoinCriteria(
			Criteria criteria) throws ResponseFailureException {
		try {
			SelectQuery selectQuery = new SelectQueryImpl(
					Table.getTable(SCREEN.TABLE));
			selectQuery.addSelectColumn(new Column(CATEGORY.TABLE, "*"));
			selectQuery.addSelectColumn(new Column(SEAT.TABLE, "*"));
			selectQuery.addSelectColumn(new Column(SCREEN.TABLE, "*"));
			selectQuery.setCriteria(criteria);
			Join screenJoin = new Join(SCREEN.TABLE, SEAT.TABLE,
					new String[] { SCREEN.SCREEN_ID },
					new String[] { SEAT.SCREEN_ID }, Join.LEFT_JOIN);
			Join categoryJoin = new Join(SEAT.TABLE, CATEGORY.TABLE,
					new String[] { SEAT.CATEGORY_ID },
					new String[] { CATEGORY.CATEGORY_ID }, Join.INNER_JOIN);

			selectQuery.addJoin(screenJoin);
			selectQuery.addJoin(categoryJoin);

			DataObject dataObject = ScreenDAOFactory.getScreenDAOInstance(
					"mickey").getDataObject(selectQuery);

			ArrayList<Seat> seatList = SeatDAOFactory.getSeatDAOInstance(
					"mickey").getBeans(dataObject.getRows(SEAT.TABLE));
			ArrayList<Screen> screenList = ScreenDAOFactory
					.getScreenDAOInstance("mickey").getBeans(
							dataObject.getRows(SCREEN.TABLE));

			ScreenSeatResponseWrapper screenSeatResponseWrapper = new ScreenSeatResponseWrapper();
			screenSeatResponseWrapper.setCategories(CategoryDAOFactory
					.getCategoryDAOInstance("mickey").getBeans(
							dataObject.getRows(CATEGORY.TABLE)));
			screenSeatResponseWrapper.setSeats(seatList);

			ArrayList<ScreenSeatResponseJSONWrapper> screenSeatWrapperList = new ArrayList<ScreenSeatResponseJSONWrapper>();
			for (int i = 0; i < screenList.size(); i++) {

				ArrayList<Long> seatIdArray = new ArrayList<Long>();

				ScreenSeatResponseJSONWrapper screenSeatWrapper = new ScreenSeatResponseJSONWrapper();
				Screen screen = screenList.get(i);
				ArrayList<Seat> screenSeatList = SeatDAOFactory
						.getSeatDAOInstance("mickey").getBeans(
								dataObject.getRows(SEAT.TABLE, new Criteria(
										new Column(SEAT.TABLE, SEAT.SCREEN_ID),
										screen.getId(), QueryConstants.EQUAL)));

				for (int j = 0; j < screenSeatList.size(); j++) {
					seatIdArray.add(screenSeatList.get(j).getId());
				}

				screenSeatWrapper.setScreenSeats(screen, seatIdArray);
				screenSeatWrapperList.add(screenSeatWrapper);

			}
			if (screenSeatWrapperList.size() == 1) {
				screenSeatResponseWrapper.setScreen(screenSeatWrapperList.get(0));
			} else {
				screenSeatResponseWrapper.setScreens(screenSeatWrapperList);
			}

			return screenSeatResponseWrapper;
		} catch (Exception e) {
			throw new ResponseFailureException(e.getMessage());
		}

	}

	public static MovieShowSeatResponseWrapper getShowSeatsOnJoinCriteria(
			Criteria criteria) throws ResponseFailureException {
		try {
			SelectQuery selectQuery = new SelectQueryImpl(
					Table.getTable(SHOWSEAT.TABLE));
			selectQuery.addSelectColumn(new Column(CATEGORY.TABLE, "*"));
			selectQuery.addSelectColumn(new Column(SEAT.TABLE, "*"));
			selectQuery.addSelectColumn(new Column(SHOWSEAT.TABLE, "*"));
			selectQuery.addSelectColumn(new Column(SCREEN.TABLE, "*"));
			selectQuery.addSelectColumn(new Column(SHOWDETAIL.TABLE, "*"));
			selectQuery.addSelectColumn(new Column(MOVIESHOW.TABLE, "*"));

			selectQuery.setCriteria(criteria);
			Join showSeatJoin = new Join(SHOWSEAT.TABLE, SEAT.TABLE,
					new String[] { SHOWSEAT.SEAT_ID },
					new String[] { SEAT.SEAT_ID }, Join.INNER_JOIN);
			Join categoryJoin = new Join(SEAT.TABLE, CATEGORY.TABLE,
					new String[] { SEAT.CATEGORY_ID },
					new String[] { CATEGORY.CATEGORY_ID }, Join.INNER_JOIN);
			Join movieShowJoin = new Join(SHOWSEAT.TABLE, MOVIESHOW.TABLE,
					new String[] { SHOWSEAT.MOVIE_SHOW_ID },
					new String[] { MOVIESHOW.MOVIE_SHOW_ID }, Join.INNER_JOIN);
			Join movieJoin = new Join(MOVIESHOW.TABLE, MOVIE.TABLE,
					new String[] { MOVIESHOW.MOVIE_ID },
					new String[] { MOVIE.MOVIE_ID }, Join.INNER_JOIN);
			Join showJoin = new Join(MOVIESHOW.TABLE, SHOWDETAIL.TABLE,
					new String[] { MOVIESHOW.SHOW_ID },
					new String[] { SHOWDETAIL.SHOW_ID }, Join.INNER_JOIN);
			Join screenJoin = new Join(MOVIESHOW.TABLE, SCREEN.TABLE,
					new String[] { MOVIESHOW.SCREEN_ID },
					new String[] { SCREEN.SCREEN_ID }, Join.INNER_JOIN);

			selectQuery.addJoin(movieShowJoin);
			selectQuery.addJoin(showSeatJoin);
			selectQuery.addJoin(categoryJoin);
			selectQuery.addJoin(screenJoin);
			selectQuery.addJoin(showJoin);
			selectQuery.addJoin(movieJoin);

			DataObject dataObject = ShowSeatDAOFactory.getShowSeatDAOInstance(
					"mickey").getDataObject(selectQuery);

			MovieShowSeatResponseWrapper movieShowSeatResponseWrapper = new MovieShowSeatResponseWrapper();
			movieShowSeatResponseWrapper.setShowseats(ShowSeatDAOFactory
					.getShowSeatDAOInstance("mickey").getBeans(
							dataObject.getRows(SHOWSEAT.TABLE)));
			movieShowSeatResponseWrapper.setSeats(SeatDAOFactory
					.getSeatDAOInstance("mickey").getBeans(
							dataObject.getRows(SEAT.TABLE)));
			movieShowSeatResponseWrapper.setCategories(CategoryDAOFactory
					.getCategoryDAOInstance("mickey").getBeans(
							dataObject.getRows(CATEGORY.TABLE)));
			movieShowSeatResponseWrapper.setMovieShows(MovieShowDAOFactory
					.getMovieShowDAOInstance("mickey").getBeans(
							dataObject.getRows(MOVIESHOW.TABLE)));
			movieShowSeatResponseWrapper.setScreens(ScreenDAOFactory
					.getScreenDAOInstance("mickey").getBeans(
							dataObject.getRows(SCREEN.TABLE)));
			movieShowSeatResponseWrapper.setShows(ShowDAOFactory
					.getShowDAOInstance("mickey").getBeans(
							dataObject.getRows(SHOWDETAIL.TABLE)));
			movieShowSeatResponseWrapper.setMovies(MovieDAOFactoy
					.getMovieDAOInstance("mickey").getBeans(
							dataObject.getRows(MOVIE.TABLE)));

			return movieShowSeatResponseWrapper;

		} catch (Exception e) {
			throw new ResponseFailureException(e.getMessage());
		}

	}

	public static TicketResponseWrapper getTicketDetail(Long ticketId)
			throws ResponseFailureException {

		try {

			SelectQuery joinQuery = new SelectQueryImpl(
					Table.getTable(TICKET.TABLE));
			Criteria criteria = new Criteria(new Column(TICKET.TABLE,
					TICKET.TICKET_ID), ticketId, QueryConstants.EQUAL);

			joinQuery.addSelectColumn(new Column(SHOWDETAIL.TABLE, "*"));
			joinQuery.addSelectColumn(new Column(SEAT.TABLE, "*"));
			joinQuery.addSelectColumn(new Column(CATEGORY.TABLE, "*"));
			joinQuery.addSelectColumn(new Column(TICKET.TABLE, "*"));
			joinQuery.addSelectColumn(new Column(CUSTOMER.TABLE, "*"));
			joinQuery.addSelectColumn(new Column(SCREEN.TABLE, "*"));
			joinQuery.addSelectColumn(new Column(MOVIE.TABLE, "*"));
			joinQuery.addSelectColumn(new Column(MOVIESHOW.TABLE, "*"));
			joinQuery.addSelectColumn(new Column(SHOWDETAIL.TABLE, "*"));

			joinQuery.setCriteria(criteria);

			Join seatJoin = new Join(SHOWSEAT.TABLE, SEAT.TABLE,
					new String[] { SHOWSEAT.SEAT_ID },
					new String[] { SEAT.SEAT_ID }, Join.INNER_JOIN);
			Join categoryJoin = new Join(SEAT.TABLE, CATEGORY.TABLE,
					new String[] { SEAT.CATEGORY_ID },
					new String[] { CATEGORY.CATEGORY_ID }, Join.INNER_JOIN);
			Join showSeatJoin = new Join(TICKET.TABLE, SHOWSEAT.TABLE,
					new String[] { TICKET.TICKET_ID },
					new String[] { SHOWSEAT.TICKET_ID }, Join.INNER_JOIN);
			Join customerJoin = new Join(TICKET.TABLE, CUSTOMER.TABLE,
					new String[] { TICKET.CUSTOMER_ID },
					new String[] { CUSTOMER.CUSTOMER_ID }, Join.INNER_JOIN);
			Join movieShowJoin = new Join(TICKET.TABLE, MOVIESHOW.TABLE,
					new String[] { TICKET.MOVIE_SHOW_ID },
					new String[] { MOVIESHOW.MOVIE_SHOW_ID }, Join.INNER_JOIN);
			Join movieJoin = new Join(MOVIESHOW.TABLE, MOVIE.TABLE,
					new String[] { MOVIESHOW.MOVIE_ID },
					new String[] { MOVIE.MOVIE_ID }, Join.INNER_JOIN);
			Join showJoin = new Join(MOVIESHOW.TABLE, SHOWDETAIL.TABLE,
					new String[] { MOVIESHOW.SHOW_ID },
					new String[] { SHOWDETAIL.SHOW_ID }, Join.INNER_JOIN);
			Join screenJoin = new Join(MOVIESHOW.TABLE, SCREEN.TABLE,
					new String[] { MOVIESHOW.SCREEN_ID },
					new String[] { SCREEN.SCREEN_ID }, Join.INNER_JOIN);

			joinQuery.addJoin(showSeatJoin);
			joinQuery.addJoin(seatJoin);
			joinQuery.addJoin(categoryJoin);

			joinQuery.addJoin(customerJoin);

			joinQuery.addJoin(movieShowJoin);
			joinQuery.addJoin(screenJoin);
			joinQuery.addJoin(showJoin);
			joinQuery.addJoin(movieJoin);

			DataObject dataObject = TicketDAOFactory.getTicketDAOInstance(
					"mickey").getDataObject(joinQuery);

			SelectQuery chargesJoinQuery = new SelectQueryImpl(
					Table.getTable(TICKET.TABLE));
			chargesJoinQuery.setCriteria(criteria);
			chargesJoinQuery.addSelectColumn(new Column(TICKET.TABLE, "*"));
			chargesJoinQuery.addSelectColumn(new Column(EXTRA.TABLE, "*"));
			chargesJoinQuery
					.addSelectColumn(new Column(TICKETCHARGE.TABLE, "*"));
			Join chargeJoin = new Join(TICKET.TABLE, TICKETCHARGE.TABLE,
					new String[] { TICKET.TICKET_ID },
					new String[] { TICKETCHARGE.TICKET_ID }, Join.LEFT_JOIN);
			Join extraJoin = new Join(TICKETCHARGE.TABLE, EXTRA.TABLE,
					new String[] { TICKETCHARGE.EXTRA_ID },
					new String[] { EXTRA.EXTRA_ID }, Join.LEFT_JOIN);
			chargesJoinQuery.addJoin(chargeJoin);
			chargesJoinQuery.addJoin(extraJoin);
			DataObject chargesDataObject = TicketDAOFactory
					.getTicketDAOInstance("mickey").getDataObject(
							chargesJoinQuery);

			ArrayList<Seat> seatList = SeatDAOFactory.getSeatDAOInstance(
					"mickey").getBeans(dataObject.getRows(SEAT.TABLE));

			ArrayList<TicketCharge> ticketChargeList = TicketChargeDAOFactory
					.getTicketChargeDAOInstance("mickey").getBeans(
							chargesDataObject.getRows(TICKETCHARGE.TABLE));

			ArrayList<Long> seatIdArray = new ArrayList<Long>();
			ArrayList<Long> ticketChargeIdArray = new ArrayList<Long>();

			for (int i = 0; i < seatList.size(); i++) {
				Seat seat = seatList.get(i);
				seatIdArray.add(seat.getId());
			}

			for (int i = 0; i < ticketChargeList.size(); i++) {
				ticketChargeIdArray.add(ticketChargeList.get(i).getId());

			}

			TicketResponseWrapper ticketResponseWrapper = new TicketResponseWrapper();
			Ticket ticket = (Ticket) TicketDAOFactory
					.getTicketDAOInstance("mickey")
					.getBeans(dataObject.getRows(TICKET.TABLE)).get(0);
			TicketResponseJSONWrapper ticketResponseJSONWrapper = new TicketResponseJSONWrapper();
			ticketResponseJSONWrapper.setTicketObj(ticket, seatIdArray,
					ticketChargeIdArray);
			ticketResponseWrapper.setTicket(ticketResponseJSONWrapper);
			ticketResponseWrapper.setTicketcharges(ticketChargeList);
			ticketResponseWrapper.setCategories(CategoryDAOFactory
					.getCategoryDAOInstance("mickey").getBeans(
							dataObject.getRows(CATEGORY.TABLE)));
			ticketResponseWrapper.setMovieShows(MovieShowDAOFactory
					.getMovieShowDAOInstance("mickey").getBeans(
							dataObject.getRows(MOVIESHOW.TABLE)));
			ticketResponseWrapper.setMovieShows(MovieShowDAOFactory
					.getMovieShowDAOInstance("mickey").getBeans(
							dataObject.getRows(MOVIESHOW.TABLE)));
			ticketResponseWrapper.setScreens(ScreenDAOFactory
					.getScreenDAOInstance("mickey").getBeans(
							dataObject.getRows(SCREEN.TABLE)));
			ticketResponseWrapper.setShows(ShowDAOFactory.getShowDAOInstance(
					"mickey").getBeans(dataObject.getRows(SHOWDETAIL.TABLE)));
			ticketResponseWrapper.setMovies(MovieDAOFactoy.getMovieDAOInstance(
					"mickey").getBeans(dataObject.getRows(MOVIE.TABLE)));
			ticketResponseWrapper.setExtras(ExtraDAOFactory
					.getExtraDAOInstance("mickey").getBeans(
							chargesDataObject.getRows(EXTRA.TABLE)));
			ticketResponseWrapper.setCustomers(CustomerDAOFactory
					.getCustomerDAOInstance("mickey").getBeans(
							dataObject.getRows(CUSTOMER.TABLE)));
			ticketResponseWrapper.setSeats(seatList);

			return ticketResponseWrapper;

		} catch (Exception e) {
			throw new ResponseFailureException(e.getMessage());
		}

	}

	public static boolean isTicketBookedForScreen(Long screenId)
			throws ResponseFailureException {
		SelectQuery selectQuery = new SelectQueryImpl(
				Table.getTable(MOVIESHOW.TABLE));
		Criteria criteria = new Criteria(new Column(MOVIESHOW.TABLE,
				MOVIESHOW.DATE), new Date(), QueryConstants.GREATER_EQUAL);
		criteria = criteria.and(new Criteria(new Column(SEAT.TABLE,
				SEAT.SCREEN_ID), screenId, QueryConstants.EQUAL));
		criteria = criteria.and(new Criteria(new Column(MOVIESHOW.TABLE,
				MOVIESHOW.SCREEN_ID), screenId, QueryConstants.EQUAL));
		criteria = criteria.and(new Column(SHOWSEAT.TABLE, SHOWSEAT.TICKET_ID),
				null, QueryConstants.NOT_EQUAL);
		selectQuery.addSelectColumn(new Column(SCREEN.TABLE, "*"));
		selectQuery.addSelectColumn(new Column(MOVIESHOW.TABLE, "*"));
		selectQuery.addSelectColumn(new Column(SHOWSEAT.TABLE, "*"));
		selectQuery.addSelectColumn(new Column(SEAT.TABLE, "*"));
		selectQuery.setCriteria(criteria);
		Join seatJoin = new Join(SEAT.TABLE, SHOWSEAT.TABLE,
				new String[] { SEAT.SEAT_ID },
				new String[] { SHOWSEAT.SEAT_ID }, Join.INNER_JOIN);
		Join movieShowJoin = new Join(MOVIESHOW.TABLE, SCREEN.TABLE,
				new String[] { MOVIESHOW.SCREEN_ID },
				new String[] { SCREEN.SCREEN_ID }, Join.INNER_JOIN);
		Join screenJoin = new Join(SCREEN.TABLE, SEAT.TABLE,
				new String[] { SCREEN.SCREEN_ID },
				new String[] { SEAT.SCREEN_ID }, Join.INNER_JOIN);
		selectQuery.addJoin(movieShowJoin);
		selectQuery.addJoin(screenJoin);
		selectQuery.addJoin(seatJoin);
		DataObject dataObject = TicketDAOFactory.getTicketDAOInstance("mickey")
				.getDataObject(selectQuery);
		try {
			if (((Iterator) dataObject.getRows(SHOWSEAT.TABLE)).hasNext()) {
				return true;
			}
			return false;
		} catch (Exception e) {
			throw new ResponseFailureException(e.getMessage());
		}

	}

	public static boolean isTicketBookedForShow(Long showId)
			throws ResponseFailureException {
		SelectQuery selectQuery = new SelectQueryImpl(
				Table.getTable(SHOWDETAIL.TABLE));
		Criteria criteria = new Criteria(new Column(SHOWSEAT.TABLE,
				SHOWSEAT.TICKET_ID), null, QueryConstants.NOT_EQUAL);
		criteria = criteria.and(new Criteria(new Column(SHOWDETAIL.TABLE,
				SHOWDETAIL.SHOW_ID), showId, QueryConstants.EQUAL));
		criteria = criteria.and(new Criteria(new Column(MOVIESHOW.TABLE,
				MOVIESHOW.DATE), new Date(), QueryConstants.GREATER_EQUAL));
		selectQuery.addSelectColumn(new Column(SHOWSEAT.TABLE, "*"));
		selectQuery.addSelectColumn(new Column(MOVIESHOW.TABLE, "*"));
		selectQuery.addSelectColumn(new Column(SHOWSEAT.TABLE, "*"));
		selectQuery.setCriteria(criteria);
		Join movieShowJoin = new Join(MOVIESHOW.TABLE, SHOWSEAT.TABLE,
				new String[] { MOVIESHOW.MOVIE_SHOW_ID },
				new String[] { SHOWSEAT.MOVIE_SHOW_ID }, Join.INNER_JOIN);
		Join showJoin = new Join(SHOWDETAIL.TABLE, MOVIESHOW.TABLE,
				new String[] { SHOWDETAIL.SHOW_ID },
				new String[] { MOVIESHOW.SHOW_ID }, Join.INNER_JOIN);
		selectQuery.addJoin(showJoin);
		selectQuery.addJoin(movieShowJoin);
		DataObject dataObject = TicketDAOFactory.getTicketDAOInstance("mickey")
				.getDataObject(selectQuery);

		try {
			if (((Iterator) dataObject.getRows(SHOWSEAT.TABLE)).hasNext()) {
				return true;
			}
			return false;
		} catch (Exception e) {
			throw new ResponseFailureException(e.getMessage());
		}

	}

	public static SeatResponseWrapper getSeatOnJoinCriteria(Criteria criteria)
			throws ResponseFailureException {

		try {
			SelectQuery selectQuery = new SelectQueryImpl(
					Table.getTable(SEAT.TABLE));
			selectQuery.addSelectColumn(new Column(CATEGORY.TABLE, "*"));
			selectQuery.addSelectColumn(new Column(SEAT.TABLE, "*"));
			selectQuery.setCriteria(criteria);
			Join categoryJoin = new Join(SEAT.TABLE, CATEGORY.TABLE,
					new String[] { SEAT.CATEGORY_ID },
					new String[] { CATEGORY.CATEGORY_ID }, Join.INNER_JOIN);

			selectQuery.addJoin(categoryJoin);

			DataObject dataObject = ScreenDAOFactory.getScreenDAOInstance(
					"mickey").getDataObject(selectQuery);

			Seat seat = (Seat) SeatDAOFactory.getSeatDAOInstance("mickey")
					.getBeans(dataObject.getRows(SEAT.TABLE)).get(0);

			SeatResponseWrapper seatResponseWrapper = new SeatResponseWrapper();
			seatResponseWrapper.setCategory((Category) CategoryDAOFactory
					.getCategoryDAOInstance("mickey")
					.getBeans(dataObject.getRows(CATEGORY.TABLE)).get(0));
			seatResponseWrapper.setSeat(seat);

			return seatResponseWrapper;
		} catch (Exception e) {
			throw new ResponseFailureException(e.getMessage());
		}
	}

}
