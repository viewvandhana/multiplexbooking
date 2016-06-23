package com.adventnet.moviebooking;

/** <p> Description of the table <code>Category</code>.
 *  Column Name and Table Name of  database table  <code>Category</code> is mapped
 * as constants in this util.</p> 
   * 
  * Primary Key for this definition is  <br>
  <ul>
  * <li> {@link #CATEGORY_ID}
  * </ul>
 */
 
public final class CATEGORY
{
    private CATEGORY()
    {
    }
   
    /** Constant denoting the Table Name of this definition.
     */
    public static final String TABLE = "Category" ;
    /**
                            * This column is an Primary Key for this Table definition. <br>
                            * Data Type of this field is <code>BIGINT</code>. <br>
                                          * This field is nullable. <br>
                                */
    public static final String CATEGORY_ID= "CATEGORY_ID" ;

    /*
    * The index position of the column CATEGORY_ID in the table.
    */
    public static final int CATEGORY_ID_IDX = 1 ;

    /**
                            * Data Type of this field is <code>CHAR</code>. <br>
                     * Maximum length of this field value is <code>150</code>. <br>
                                   * This field is nullable. <br>
                                */
    public static final String CATEGORY_NAME= "CATEGORY_NAME" ;

    /*
    * The index position of the column CATEGORY_NAME in the table.
    */
    public static final int CATEGORY_NAME_IDX = 2 ;

    /**
                            * Data Type of this field is <code>FLOAT</code>. <br>
                                          * This field is nullable. <br>
                                */
    public static final String CATEGORY_FARE= "CATEGORY_FARE" ;

    /*
    * The index position of the column CATEGORY_FARE in the table.
    */
    public static final int CATEGORY_FARE_IDX = 3 ;

}
