package com.adventnet.moviebooking;

/** <p> Description of the table <code>Ticket</code>.
 *  Column Name and Table Name of  database table  <code>Ticket</code> is mapped
 * as constants in this util.</p> 
   * 
  * Primary Key for this definition is  <br>
  <ul>
  * <li> {@link #TICKET_ID}
  * </ul>
 */
 
public final class TICKET
{
    private TICKET()
    {
    }
   
    /** Constant denoting the Table Name of this definition.
     */
    public static final String TABLE = "Ticket" ;
    /**
                            * This column is an Primary Key for this Table definition. <br>
                            * Data Type of this field is <code>BIGINT</code>. <br>
                                          * This field is nullable. <br>
                                */
    public static final String TICKET_ID= "TICKET_ID" ;

    /*
    * The index position of the column TICKET_ID in the table.
    */
    public static final int TICKET_ID_IDX = 1 ;

    /**
                            * Data Type of this field is <code>BIGINT</code>. <br>
                                          * This field is nullable. <br>
                                */
    public static final String MOVIE_SHOW_ID= "MOVIE_SHOW_ID" ;

    /*
    * The index position of the column MOVIE_SHOW_ID in the table.
    */
    public static final int MOVIE_SHOW_ID_IDX = 2 ;

    /**
                            * Data Type of this field is <code>BIGINT</code>. <br>
                                          * This field is nullable. <br>
                                */
    public static final String CUSTOMER_ID= "CUSTOMER_ID" ;

    /*
    * The index position of the column CUSTOMER_ID in the table.
    */
    public static final int CUSTOMER_ID_IDX = 3 ;

    /**
                            * Data Type of this field is <code>FLOAT</code>. <br>
                                          * This field is nullable. <br>
                                */
    public static final String TOTAL_COST= "TOTAL_COST" ;

    /*
    * The index position of the column TOTAL_COST in the table.
    */
    public static final int TOTAL_COST_IDX = 4 ;

}
