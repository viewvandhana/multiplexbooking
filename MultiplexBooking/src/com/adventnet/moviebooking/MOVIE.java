package com.adventnet.moviebooking;

/** <p> Description of the table <code>Movie</code>.
 *  Column Name and Table Name of  database table  <code>Movie</code> is mapped
 * as constants in this util.</p> 
   * 
  * Primary Key for this definition is  <br>
  <ul>
  * <li> {@link #MOVIE_ID}
  * </ul>
 */
 
public final class MOVIE
{
    private MOVIE()
    {
    }
   
    /** Constant denoting the Table Name of this definition.
     */
    public static final String TABLE = "Movie" ;
    /**
                            * This column is an Primary Key for this Table definition. <br>
                            * Data Type of this field is <code>BIGINT</code>. <br>
                                          * This field is nullable. <br>
                                */
    public static final String MOVIE_ID= "MOVIE_ID" ;

    /*
    * The index position of the column MOVIE_ID in the table.
    */
    public static final int MOVIE_ID_IDX = 1 ;

    /**
                            * Data Type of this field is <code>CHAR</code>. <br>
                     * Maximum length of this field value is <code>150</code>. <br>
                                   * This field is nullable. <br>
                                */
    public static final String MOVIE_NAME= "MOVIE_NAME" ;

    /*
    * The index position of the column MOVIE_NAME in the table.
    */
    public static final int MOVIE_NAME_IDX = 2 ;

    /**
                            * Data Type of this field is <code>CHAR</code>. <br>
                     * Maximum length of this field value is <code>150</code>. <br>
                                   * This field is nullable. <br>
                                */
    public static final String GENRE= "GENRE" ;

    /*
    * The index position of the column GENRE in the table.
    */
    public static final int GENRE_IDX = 3 ;

    /**
                            * Data Type of this field is <code>CHAR</code>. <br>
                     * Maximum length of this field value is <code>150</code>. <br>
                                   * This field is nullable. <br>
                                */
    public static final String CATEGORY= "CATEGORY" ;

    /*
    * The index position of the column CATEGORY in the table.
    */
    public static final int CATEGORY_IDX = 4 ;

    /**
                            * Data Type of this field is <code>CHAR</code>. <br>
                     * Maximum length of this field value is <code>150</code>. <br>
                                   * This field is nullable. <br>
                                */
    public static final String CERTIFICATE= "CERTIFICATE" ;

    /*
    * The index position of the column CERTIFICATE in the table.
    */
    public static final int CERTIFICATE_IDX = 5 ;

    /**
                            * Data Type of this field is <code>CHAR</code>. <br>
                     * Maximum length of this field value is <code>150</code>. <br>
                                   * This field is nullable. <br>
                                */
    public static final String LANGUAGE= "LANGUAGE" ;

    /*
    * The index position of the column LANGUAGE in the table.
    */
    public static final int LANGUAGE_IDX = 6 ;

    /**
                            * Data Type of this field is <code>CHAR</code>. <br>
                                          * This field is nullable. <br>
                                */
    public static final String DURATION= "DURATION" ;

    /*
    * The index position of the column DURATION in the table.
    */
    public static final int DURATION_IDX = 7 ;

    /**
                            * Data Type of this field is <code>CHAR</code>. <br>
                     * Maximum length of this field value is <code>500</code>. <br>
                                   * This field is nullable. <br>
                                */
    public static final String DESCRIPTION= "DESCRIPTION" ;

    /*
    * The index position of the column DESCRIPTION in the table.
    */
    public static final int DESCRIPTION_IDX = 8 ;

    /**
                            * Data Type of this field is <code>CHAR</code>. <br>
                     * Maximum length of this field value is <code>1000</code>. <br>
                                   * This field is nullable. <br>
                                */
    public static final String IMAGE_URL= "IMAGE_URL" ;

    /*
    * The index position of the column IMAGE_URL in the table.
    */
    public static final int IMAGE_URL_IDX = 9 ;

    /**
                            * Data Type of this field is <code>DATE</code>. <br>
                                          * This field is nullable. <br>
                                */
    public static final String DATE_RELEASED= "DATE_RELEASED" ;

    /*
    * The index position of the column DATE_RELEASED in the table.
    */
    public static final int DATE_RELEASED_IDX = 10 ;

}
