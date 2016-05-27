package app.popularmovies.sanjana.com.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {
    private ReviewAdapter mMovieAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mMovieAdapter = new ReviewAdapter(getApplicationContext(), new ArrayList<Review>());
        ListView gridView = (ListView) findViewById(R.id.listView);
        gridView.setAdapter(mMovieAdapter);
        String movie_id_txt = getIntent().getStringExtra("movie_id_txt");


       // Toast.makeText(this,movie_id_txt+"",Toast.LENGTH_LONG).show();

    }

    private void updateWeather() {

        FetchMovieTask obj = new FetchMovieTask();

        String movie_id_txt = getIntent().getStringExtra("movie_id_txt");
            obj.execute(movie_id_txt);

    }

    @Override
    public void onStart() {
        super.onStart();
        updateWeather();
    }

    public class FetchMovieTask extends AsyncTask<String,Void,Review[]>

    {


        private Review[] getMovieDataFromJson(String forecastJsonStr)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String OWM_LIST = "results";




            JSONObject forecastJson = new JSONObject(forecastJsonStr);
            JSONArray reviewArray = forecastJson.getJSONArray(OWM_LIST);

            //Log.v(LOG_TAG, "---Forecast entry:--- " + movieArray);



            Review[] resultStrs = new Review[reviewArray.length()];

            for(int i = 0; i < reviewArray.length(); i++) {

                String author;
                String content;




                String basic_url = "http://image.tmdb.org/t/p/w185";

                // Get the JSON object representing the movie
                JSONObject movieData = reviewArray.getJSONObject(i);


                author = movieData.getString("author");
                content = movieData.getString("content");

                resultStrs[i] = new Review(author,content) ;
            }

            for (Review s : resultStrs) {
                Log.v(LOG_TAG, "Forecast entry: " +resultStrs.length+"   "+ s.author);
            }
            return resultStrs;

        }



        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

        @Override
        protected Review[] doInBackground(String... params) {

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String movieJsonStr = null;
            String format = "json";



            //String appid= "0b1fcbe54cd78a611f318d33e9913ad0";
            String appid= "";

            //String flag = params[0];

            try {

                String FORECAST_BASE_URL = "" ;


                http://api.themoviedb.org/3/movie/76341/reviews?api_key=0b1fcbe54cd78a611f318d33e9913ad0

                FORECAST_BASE_URL = "http://api.themoviedb.org/3/movie/"+params[0]+"/reviews"+"?" ;
                final  String QUERY_PARAM = "q" ;
                final  String FORMAT_PARAM = "mode" ;

                final  String APP_ID = "api_key" ;

                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter(FORMAT_PARAM, format)
                        .appendQueryParameter(APP_ID, appid)
                        .build();

                URL url = new URL(builtUri.toString());
                Log.v(LOG_TAG, "--URL--- " +url);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                movieJsonStr = buffer.toString();
                Log.v(LOG_TAG, "Movie Json String " +movieJsonStr);


                try {
                    //resultStrs =    getMovieDataFromJson(forecastJsonStr,  numdays);

                    return(getMovieDataFromJson(movieJsonStr));


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }

            return null;
        }



        @Override
        protected void onPostExecute(Review[] result) {

            if(result!= null) {

                for (Review movie : result)

                    Log.v(LOG_TAG, "-yes-result--- " + movie);
            }

            else {



                    Log.v(LOG_TAG, "-no-result--- " + "no value");
            }

            if(result!= null)
            {
                mMovieAdapter.clear();

                for(Review movie: result)
                    mMovieAdapter.add(movie);

            }

            mMovieAdapter.notifyDataSetChanged();
            // super.onPostExecute(result);
        }



    }
}
