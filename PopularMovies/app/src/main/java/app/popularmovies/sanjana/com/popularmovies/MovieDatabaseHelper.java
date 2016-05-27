package app.popularmovies.sanjana.com.popularmovies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sanjana on 5/18/16.
 */
public class MovieDatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "popularmovie";

    // Contacts table name
    private static final String TABLE_MOVIE = "movies";
    String original_title;
    String vote_average_txt;
    String final_image_url;
    String overview;
    String releaseDate;
    double vote_average;
    String movie_id;
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_ORIGINAL_TITLE = "original_title";

    private static final String KEY_VOTE_AVG_TEXT = "vote_avg_text";
    private static final String KEY_IMAGE_URL= "image_url";

    public static final String KEY_OVERVIEW = "overview";
    private static final String KEY_VOTE_AVG = "vote_avg";
    private static final String KEY_MOVIE_ID = "movie_id";

    private static final String KEY_RELEASE_DATE = "release_date";
    private static final String KEY_MOVIE_ID_TEXT = "movie_id_txt";

    public MovieDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FAVORITE_MOVIE_TABLE = "CREATE TABLE " + TABLE_MOVIE + "(" + KEY_ID
                + " INTEGER PRIMARY KEY," + KEY_ORIGINAL_TITLE + " TEXT," + KEY_VOTE_AVG_TEXT
                + " TEXT," + KEY_IMAGE_URL + " TEXT," + KEY_OVERVIEW
                + " TEXT,"
                 + KEY_VOTE_AVG + " DOUBLE," + KEY_MOVIE_ID
                + " INTEGER,"
                + KEY_RELEASE_DATE
                + " TEXT,"
                +

                KEY_MOVIE_ID_TEXT
                + " TEXT"
                +

                ")";




        db.execSQL(CREATE_FAVORITE_MOVIE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIE);

        // Create tables again
        onCreate(db);
    }

    /**
     * Adding a new website in websites table Function will check if a site
     * already existed in database. If existed will update the old one else
     * creates a new row
     * */
    public void addMovie(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ORIGINAL_TITLE, movie.original_title); // site title
        values.put(KEY_IMAGE_URL, movie.final_image_url); // site url

        values.put(KEY_VOTE_AVG_TEXT, movie.vote_average_txt); // site title
        values.put(KEY_OVERVIEW, movie.overview); // site url

        values.put(KEY_VOTE_AVG, movie.vote_average); // site title
        values.put(KEY_RELEASE_DATE, movie.releaseDate); // site url
        values.put(KEY_MOVIE_ID_TEXT, movie.movie_id_txt);
        values.put(KEY_MOVIE_ID, movie.movie_id); // site title



        // Check if row already existed in database
        if (!isSiteExists(db, movie.original_title)) {
            // site not existed, create a new row
             db.insert(TABLE_MOVIE, null, values);
            db.close();

        } else {
            // site already existed update the row

            db.close();
        }

    }

    /**
     * Reading all rows from database
     *
     *
     *
     * */
    public List<Movie> getAllSites() {
        List<Movie> siteList = new ArrayList<Movie>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MOVIE
                + " ORDER BY id DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                Movie site = new Movie();
                site.setFinal_image_url(cursor.getString(cursor.getColumnIndex("image_url")));
                site.setReleaseDate(cursor.getString(cursor.getColumnIndex("release_date")));
                site.setOriginal_title(cursor.getString(cursor.getColumnIndex("original_title")));

                site.setVote_average(cursor.getDouble(cursor.getColumnIndex("vote_avg")));
                site.setVote_average_txt(cursor.getString(cursor.getColumnIndex("vote_avg_text")));
                site.setMovie_id(cursor.getInt(cursor.getColumnIndex("movie_id")));
                site.setMovie_id_txt(cursor.getString(cursor.getColumnIndex("movie_id_txt")));
                site.setOverview(cursor.getString(cursor.getColumnIndex("overview")));

                // Adding contact to list
                siteList.add(site);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return sitelist
        return siteList;
    }

    /**
     * Checking whether a movie is already existed check is done by matching rss
     * link
     * */
    public boolean isSiteExists(SQLiteDatabase db, String original_title) {

        Cursor cursor = db.rawQuery("SELECT 1 FROM " + TABLE_MOVIE
                + " WHERE original_title = '" + original_title + "'", new String[] {});
        boolean exists = (cursor.getCount() > 0);
        return exists;
    }







}