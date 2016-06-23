package com.adventnet.moviebooking;

/** <p> Description of the table <code>ShowSeat</code>.
 *  Column Name and Table Name of  database table  <code>ShowSeat</code> is mapped
 * as constants in this util.</p> 
   * 
  * Primary Key for this definition is  <br>
  <ul>
  * <li> {@link #SHOW_SEAT_ID}
  * </ul>
 */
 
public final class SHOWSEAT
{
    private SHOWSEAT()
    {
    }
   
    /** Constant denoting the Table Name of this definition.
     */
    public static final String TABLE = "ShowSeat" ;
    /**
                            * This column is an Primary Key for this Table definition. <br>
                            * Data Type of this field is <code>BIGINT</code>. <br>
                                          * This field is nullable. <br>
                                */
    public static final String SHOW_SEAT_ID= "SHOW_SEAT_ID" ;

    /*
    * The index position of the column SHOW_SEAT_ID in the table.
    */
    public static final int SHOW_SEAT_ID_IDX = 1 ;

    /**
                            * Data Type of this field is <code>BIGINT</code>. <br>
                                          * This field is not nullable. <br>
                                */
    public static final String TICKET_ID= "TICKET_ID" ;

    /*
    * The index position of the column TICKET_ID in the table.
    */
    public static final int TICKET_ID_IDX = 2 ;

    /**
                            * Data Type of this field is <code>BIGINT</code>. <br>
                                          * This field is nullable. <br>
                                */
    public static final String SEAT_ID= "SEAT_ID" ;

    /*
    * The index position of the column SEAT_ID in the table.
    */
    public static final int SEAT_ID_IDX = 3 ;

    /**
                            * Data Type of this field is <code>BIGINT</code>. <br>
                                          * This field is nullable. <br>
                                */
    public static final String MOVIE_SHOW_ID= "MOVIE_SHOW_ID" ;

    /*
    * The index position of the column MOVIE_SHOW_ID in the table.
    */
    public static final int MOVIE_SHOW_ID_IDX = 4 ;

}
