package com.adventnet.moviebooking;

/** <p> Description of the table <code>MovieShow</code>.
 *  Column Name and Table Name of  database table  <code>MovieShow</code> is mapped
 * as constants in this util.</p> 
   * 
  * Primary Key for this definition is  <br>
  <ul>
  * <li> {@link #MOVIE_SHOW_ID}
  * </ul>
 */
 
public final class MOVIESHOW
{
    private MOVIESHOW()
    {
    }
   
    /** Constant denoting the Table Name of this definition.
     */
    public static final String TABLE = "MovieShow" ;
    /**
                            * This column is an Primary Key for this Table definition. <br>
                            * Data Type of this field is <code>BIGINT</code>. <br>
                                          * This field is nullable. <br>
                                */
    public static final String MOVIE_SHOW_ID= "MOVIE_SHOW_ID" ;

    /*
    * The index position of the column MOVIE_SHOW_ID in the table.
    */
    public static final int MOVIE_SHOW_ID_IDX = 1 ;

    /**
                            * Data Type of this field is <code>BIGINT</code>. <br>
                                          * This field is nullable. <br>
                                */
    public static final String SCREEN_ID= "SCREEN_ID" ;

    /*
    * The index position of the column SCREEN_ID in the table.
    */
    public static final int SCREEN_ID_IDX = 2 ;

    /**
                            * Data Type of this field is <code>BIGINT</code>. <br>
                                          * This field is nullable. <br>
                                */
    public static final String SHOW_ID= "SHOW_ID" ;

    /*
    * The index position of the column SHOW_ID in the table.
    */
    public static final int SHOW_ID_IDX = 3 ;

    /**
                            * Data Type of this field is <code>BIGINT</code>. <br>
                                          * This field is nullable. <br>
                                */
    public static final String MOVIE_ID= "MOVIE_ID" ;

    /*
    * The index position of the column MOVIE_ID in the table.
    */
    public static final int MOVIE_ID_IDX = 4 ;

    /**
                            * Data Type of this field is <code>DATE</code>. <br>
                                          * This field is nullable. <br>
                                */
    public static final String DATE= "DATE" ;

    /*
    * The index position of the column DATE in the table.
    */
    public static final int DATE_IDX = 5 ;

}
