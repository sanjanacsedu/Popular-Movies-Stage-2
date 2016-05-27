package app.popularmovies.sanjana.com.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

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
import java.util.List;

/**
 * A placeholder fragment containing a simple view.

        original title
        movie poster image thumbnail
        A plot synopsis (called overview in the api)
        user rating (called vote_average in the api)
        release date
 */
public class MainActivityFragment extends Fragment {

    int flag = 0;
    private Boolean mTablet;
    private ImageAdapter mMovieAdapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        Context context = getActivity();
        mMovieAdapter = new ImageAdapter(context, new ArrayList<Movie>());
        GridView gridView = (GridView) rootView.findViewById(R.id.gridView);
        gridView.setAdapter(mMovieAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
         Movie movie = mMovieAdapter.getItem(position);
        //Toast.makeText(getContext(),movie.final_image_url+"",Toast.LENGTH_LONG).show();
         mTablet = ((MainActivity) getActivity()).isTablet();

             if(mTablet){
                    //This is tablet mode, tell MainActivity to replace the fragment in Container
                    ((MainActivity)getActivity()).replaceFragment(movie.releaseDate,movie.original_title,movie.overview,
                            movie.final_image_url,movie.vote_average, movie.vote_average_txt, movie.movie_id, movie.movie_id_txt  );

                }

                else  {
                    //This is phone mode, create an intent to launch DetailActivity.  Attach EXTRA
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra("EXTRA", "This fragment is attahced to a Detail Activity").
                            putExtra("releaseDate",movie.releaseDate).
                            putExtra("original_title",movie.original_title).
                            putExtra("overview",movie.overview).
                            putExtra("final_image_url",movie.final_image_url).
                            putExtra("vote_average",movie.vote_average).
                            putExtra("vote_average_txt",movie.vote_average_txt).
                            putExtra("movie_id",movie.movie_id).
                            putExtra("movie_id_txt",movie.movie_id_txt);

                    //;
                    startActivity(intent);

                }



               // startActivity(obj);


            }
        });
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_main, menu);
        //return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

       else  if (id == R.id.action_popular) {

            //Toast.makeText(getActivity(),"popular",Toast.LENGTH_LONG).show();

            FetchMovieTask obj = new FetchMovieTask();
            flag = 1;
            obj.execute("popular");
            return true;
        }

        else  if (id == R.id.action_rating) {

            //Toast.makeText(getActivity(),"rating",Toast.LENGTH_LONG).show();
            FetchMovieTask obj = new FetchMovieTask();
            flag = 2;
            obj.execute("rating");
            return true;
        }


        else  if (id == R.id.action_favourite) {

            //Toast.makeText(getActivity(),"rating",Toast.LENGTH_LONG).show();
            FetchMovieTask obj = new FetchMovieTask();
            flag = 3;
            obj.execute("favourite");
            return true;
        }
        return super.onOptionsItemSelected(item);

}
    private void updateWeather() {

        FetchMovieTask obj = new FetchMovieTask();

        if(flag == 2) obj.execute("rating");
        else if(flag == 3) obj.execute("favorite");
        else
        obj.execute("popular");

    }

    @Override
    public void onStart() {
        super.onStart();
        updateWeather();
    }


    public class FetchMovieTask extends AsyncTask<String,Void,Movie[]>

    {

        private Movie[] getMovieDataFromDB()
                 {

            // These are the names of the JSON objects that need to be extracted.
            final String OWM_LIST = "results";
         MovieDatabaseHelper movieDb = new MovieDatabaseHelper(
                             getContext());
                     // listing all websites from SQLite
                     List<Movie> siteList = movieDb.getAllSites();

                     //sqliteIds = new String[siteList.size()];


                     Movie[] resultStrs = new Movie[siteList.size()];

            for(int i = 0; i < siteList.size(); i++) {

                String releaseDate;
                String overview;
                String original_title;
                String image_url;
                double vote_average;
                String vote_average_txt;
                String movie_id_txt;

                String final_image_url_db;
                int movie_id;

                String basic_url = "http://image.tmdb.org/t/p/w185";

                // Get the JSON object representing the movie
                Movie s = siteList.get(i);


                image_url = s.final_image_url;
                original_title = s.original_title;
                overview = s.overview;
                releaseDate = s.releaseDate;
                vote_average = s.vote_average;
                vote_average_txt = s.vote_average_txt;
                movie_id = s.movie_id;
                movie_id_txt = s.movie_id_txt;

                final_image_url_db = basic_url+image_url;

                resultStrs[i] = new Movie(final_image_url_db,original_title,overview,releaseDate,vote_average,vote_average_txt,movie_id,movie_id_txt) ;
            }

            for (Movie s : resultStrs) {
                Log.v(LOG_TAG, "Forecast entry: " +resultStrs.length+"   "+ s.releaseDate);
            }
            return resultStrs;

        }

        private Movie[] getMovieDataFromJson(String forecastJsonStr)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String OWM_LIST = "results";


            JSONObject forecastJson = new JSONObject(forecastJsonStr);
            JSONArray movieArray = forecastJson.getJSONArray(OWM_LIST);


            Movie[] resultStrs = new Movie[movieArray.length()];

            for(int i = 0; i < movieArray.length(); i++) {

                String releaseDate;
                String overview;
                String original_title;
                String image_url;
                double vote_average;
                String vote_average_txt,movie_id_txt;

                String final_image_url;
                int movie_id;

                String basic_url = "http://image.tmdb.org/t/p/w185";

                // Get the JSON object representing the movie
                JSONObject movieData = movieArray.getJSONObject(i);
                image_url = movieData.getString("poster_path");
                original_title = movieData.getString("original_title");
                overview = movieData.getString("overview");
                releaseDate = movieData.getString("release_date");
                vote_average = movieData.getDouble("vote_average");
                vote_average_txt = movieData.getString("vote_average");
                movie_id = movieData.getInt("id");
                movie_id_txt = movieData.getString("id");
                final_image_url = basic_url+image_url;

                resultStrs[i] = new Movie(final_image_url,original_title,overview,releaseDate,vote_average,vote_average_txt,movie_id,movie_id_txt) ;
            }

            for (Movie s : resultStrs) {
                Log.v(LOG_TAG, "Forecast entry: " +movieArray.length()+"   "+ s.movie_id);
            }
            return resultStrs;

        }



        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

        @Override
        protected Movie[] doInBackground(String... params) {

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


                if(params[0].equals("popular"))

                {  FORECAST_BASE_URL = "http://api.themoviedb.org/3/movie/popular?" ;}

                else if(params[0].equals("rating"))

                {  FORECAST_BASE_URL = "http://api.themoviedb.org/3/movie/top_rated" ;}



                else
                    FORECAST_BASE_URL = "http://api.themoviedb.org/3/movie/popular?" ;



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

                     if(params[0].equals("favourite"))

                    {  return(getMovieDataFromDB());}

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
        protected void onPostExecute(Movie[] result) {

            if(result!= null)
            {
                mMovieAdapter.clear();

                for(Movie movie: result)
                    mMovieAdapter.add(movie);

            }

            mMovieAdapter.notifyDataSetChanged();
            mTablet = ((MainActivity) getActivity()).isTablet();
            if(mTablet){
                //This is tablet mode, tell MainActivity to replace the fragment in Container
                ((MainActivity)getActivity()).replaceFragment("","","",
                        "R.drawable.empty_image",0.0, "", 0, "");

            }
            // super.onPostExecute(result);
        }



    }
}
