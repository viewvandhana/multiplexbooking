package com.adventnet.moviebooking;

/** <p> Description of the table <code>Screen</code>.
 *  Column Name and Table Name of  database table  <code>Screen</code> is mapped
 * as constants in this util.</p> 
   * 
  * Primary Key for this definition is  <br>
  <ul>
  * <li> {@link #SCREEN_ID}
  * </ul>
 */
 
public final class SCREEN
{
    private SCREEN()
    {
    }
   
    /** Constant denoting the Table Name of this definition.
     */
    public static final String TABLE = "Screen" ;
    /**
                            * This column is an Primary Key for this Table definition. <br>
                            * Data Type of this field is <code>BIGINT</code>. <br>
                                          * This field is nullable. <br>
                                */
    public static final String SCREEN_ID= "SCREEN_ID" ;

    /*
    * The index position of the column SCREEN_ID in the table.
    */
    public static final int SCREEN_ID_IDX = 1 ;

    /**
                            * Data Type of this field is <code>CHAR</code>. <br>
                     * Maximum length of this field value is <code>150</code>. <br>
                                   * This field is nullable. <br>
                                */
    public static final String SCREEN_NAME= "SCREEN_NAME" ;

    /*
    * The index position of the column SCREEN_NAME in the table.
    */
    public static final int SCREEN_NAME_IDX = 2 ;

    /**
                            * Data Type of this field is <code>TINYINT</code>. <br>
                                          * This field is nullable. <br>
                                */
    public static final String SCREEN_ROWS= "SCREEN_ROWS" ;

    /*
    * The index position of the column SCREEN_ROWS in the table.
    */
    public static final int SCREEN_ROWS_IDX = 3 ;

    /**
                            * Data Type of this field is <code>TINYINT</code>. <br>
                                          * This field is nullable. <br>
                                */
    public static final String SCREEN_COLS= "SCREEN_COLS" ;

    /*
    * The index position of the column SCREEN_COLS in the table.
    */
    public static final int SCREEN_COLS_IDX = 4 ;

}
