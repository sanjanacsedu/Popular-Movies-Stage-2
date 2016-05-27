package app.popularmovies.sanjana.com.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {
    private final String LOG_TAG = DetailActivityFragment.class.getSimpleName();

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        Intent intent = getActivity().getIntent();
        Boolean mTablet= false;


        //Toast.makeText(getContext(),mTablet+"trailor....",Toast.LENGTH_LONG).show();


         if((intent.getStringExtra("original_title") )!=null) {

            //Toast.makeText(getContext(),"I am in phone",Toast.LENGTH_LONG).show();

            String original_title = intent.getStringExtra("original_title");
            String releaseDate = intent.getStringExtra("releaseDate");
            String overview = intent.getStringExtra("overview");
            String final_image_url = intent.getStringExtra("final_image_url");
            String vote_average_txt = intent.getStringExtra("vote_average_txt");
            double vote_average = intent.getDoubleExtra("vote_average", 0.0);
            String movie_id = intent.getStringExtra("movie_id");


            ((TextView) rootView.findViewById(R.id.original_title)).setText(original_title);

            ((TextView) rootView.findViewById(R.id.releaseDate)).setText(releaseDate);

            ImageView iconView = (ImageView) rootView.findViewById(R.id.final_image_url);
            Picasso.with(getContext()).load(final_image_url).into(iconView);
            ((RatingBar) rootView.findViewById(R.id.vote_average)).setNumStars(10);

         //   ((TextView) rootView.findViewById(R.id.movie_rating_text)).setText("Rating:" + (float) vote_average + "/ 10");

            ((RatingBar) rootView.findViewById(R.id.vote_average)).setRating((float)vote_average);


            ((TextView) rootView.findViewById(R.id.overview))
                    .setText(overview);

            ((TextView) rootView.findViewById(R.id.ratingresult))
                    .setText(vote_average_txt);

            ((TextView) rootView.findViewById(R.id.movie_id))
                    .setText(movie_id);


            final View button3 = rootView.findViewById(R.id.button3);
            MovieDatabaseHelper moviewDb = new MovieDatabaseHelper(
                    getContext());
            if(moviewDb.isSiteExists(moviewDb.getReadableDatabase(),original_title))
                button3.setBackgroundColor(0xFFFF0000);

            button3.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                /* DO SOMETHING UPON THE CLICK */

                           // Toast.makeText(getContext(),"fav....",Toast.LENGTH_LONG).show();
                            Intent intent = getActivity().getIntent();
                            if(intent!= null ) {
                                String original_title = intent.getStringExtra("original_title");
                                String releaseDate = intent.getStringExtra("releaseDate");
                                String overview = intent.getStringExtra("overview");
                                String final_image_url = intent.getStringExtra("final_image_url");
                                String vote_average_txt = intent.getStringExtra("vote_average_txt");
                                String movie_id_txt = intent.getStringExtra("movie_id_txt");
                                double vote_average = intent.getDoubleExtra("vote_average",0.0);
                                //String movie_id = intent.getStringExtra("movie_id");
                                int movie_id = intent.getIntExtra("movie_id",0);
                                MovieDatabaseHelper moviewDb = new MovieDatabaseHelper(
                                        getContext());

                                Movie site = new Movie(final_image_url,original_title,overview,releaseDate,vote_average,vote_average_txt,movie_id, movie_id_txt);

                                 moviewDb.addMovie(site);
                                 button3.setBackgroundColor(0xFFFF0000);
                            }}
                    }
            );


            final View button = rootView.findViewById(R.id.button);
            button.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                /* DO SOMETHING UPON THE CLICK */

                           // Toast.makeText(getContext(),"rating....",Toast.LENGTH_LONG).show();

                            Intent intent = getActivity().getIntent();
                            int movie_id = intent.getIntExtra("movie_id",0);
                            String movie_id_txt = intent.getStringExtra("movie_id_txt");
                            Intent obj = new Intent(getActivity(),ReviewActivity.class).putExtra("movie_id_txt",movie_id_txt);
                            startActivity(obj);
                        }
                    }
            );


            final View button2 = rootView.findViewById(R.id.button2);
            button2.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                /* DO SOMETHING UPON THE CLICK */

                            //Toast.makeText(getContext(),"trailor....",Toast.LENGTH_LONG).show();
                            Intent intent = getActivity().getIntent();
                            int movie_id = intent.getIntExtra("movie_id",0);
                            String movie_id_txt = intent.getStringExtra("movie_id_txt");
                            Intent obj = new Intent(getActivity(),TrailorActivity.class).putExtra("movie_id_txt",movie_id_txt);
                            startActivity(obj);
                        }
                    }
            );

        }



        else if(((MainActivity) getActivity()).isTablet())


        {

           // Toast.makeText(getContext(),"I am in tablet",Toast.LENGTH_LONG).show();

            Bundle bundle = this.getArguments();
            //int myInt = bundle.getInt(key, defaultValue);
            if(bundle!= null) {

                String original_title = bundle.getString("original_title");
                String releaseDate = bundle.getString("releaseDate");
                String overview = bundle.getString("overview");
                String final_image_url = bundle.getString("final_image_url");
                String vote_average_txt = bundle.getString("vote_average_txt");
                double vote_average = bundle.getDouble("vote_average", 0.0);
                String movie_id_txt = bundle.getString("movie_id_txt");
                ((TextView) rootView.findViewById(R.id.original_title)).setText(original_title);

                ((TextView) rootView.findViewById(R.id.releaseDate)).setText(releaseDate);

                ImageView iconView = (ImageView) rootView.findViewById(R.id.final_image_url);
                Picasso.with(getContext()).load(final_image_url).into(iconView);
                ((RatingBar) rootView.findViewById(R.id.vote_average)).setNumStars(10);

                //   ((TextView) rootView.findViewById(R.id.movie_rating_text)).setText("Rating:" + (float) vote_average + "/ 10");

                ((RatingBar) rootView.findViewById(R.id.vote_average)).setRating((float)vote_average);


                ((TextView) rootView.findViewById(R.id.overview))
                        .setText(overview);

                ((TextView) rootView.findViewById(R.id.ratingresult))
                        .setText(vote_average_txt);

                ((TextView) rootView.findViewById(R.id.movie_id))
                        .setText(movie_id_txt);



                final View button3 = rootView.findViewById(R.id.button3);
                MovieDatabaseHelper moviewDb = new MovieDatabaseHelper(
                        getContext());
                if(moviewDb.isSiteExists(moviewDb.getReadableDatabase(),original_title))
                    button3.setBackgroundColor(0xFFFF0000);

                button3.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                /* DO SOMETHING UPON THE CLICK */



                                Toast.makeText(getContext(),"fav....",Toast.LENGTH_LONG).show();
                                Intent intent = getActivity().getIntent();


                                Bundle bundle = getArguments();
                                String original_title = bundle.getString("original_title");
                                String releaseDate = bundle.getString("releaseDate");
                                String overview = bundle.getString("overview");
                                String final_image_url = bundle.getString("final_image_url");
                                String vote_average_txt = bundle.getString("vote_average_txt");
                                double vote_average = bundle.getDouble("vote_average", 0.0);
                                String movie_id_txt = bundle.getString("movie_id_txt");
                                int movie_id = bundle.getInt("movie_id",0);

                                    MovieDatabaseHelper moviewDb = new MovieDatabaseHelper(
                                            getContext());

                                    Movie site = new Movie(final_image_url,original_title,overview,releaseDate,vote_average,vote_average_txt,movie_id, movie_id_txt);
                                    moviewDb.addMovie(site);
                                    button3.setBackgroundColor(0xFFFF0000);
                                }
                        }
                );


                final View button = rootView.findViewById(R.id.button);
                button.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                /* DO SOMETHING UPON THE CLICK */

                                //Toast.makeText(getContext(),"rating....",Toast.LENGTH_LONG).show();
                                Bundle bundle = getArguments();
                                String original_title = bundle.getString("original_title");
                                String releaseDate = bundle.getString("releaseDate");
                                String overview = bundle.getString("overview");
                                String final_image_url = bundle.getString("final_image_url");
                                String vote_average_txt = bundle.getString("vote_average_txt");
                                double vote_average = bundle.getDouble("vote_average", 0.0);
                                String movie_id_txt = bundle.getString("movie_id_txt");

                                Intent obj = new Intent(getActivity(),ReviewActivity.class).putExtra("movie_id_txt",movie_id_txt);
                                startActivity(obj);
                            }
                        }
                );


                final View button2 = rootView.findViewById(R.id.button2);
                button2.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                /* DO SOMETHING UPON THE CLICK */

                                //Toast.makeText(getContext(),"trailor....",Toast.LENGTH_LONG).show();

                                Bundle bundle = getArguments();
                                String original_title = bundle.getString("original_title");
                                String releaseDate = bundle.getString("releaseDate");
                                String overview = bundle.getString("overview");
                                String final_image_url = bundle.getString("final_image_url");
                                String vote_average_txt = bundle.getString("vote_average_txt");
                                double vote_average = bundle.getDouble("vote_average", 0.0);
                                String movie_id_txt = bundle.getString("movie_id_txt");
                                Intent obj = new Intent(getActivity(),TrailorActivity.class).putExtra("movie_id_txt",movie_id_txt);
                                startActivity(obj);
                            }
                        }
                );
            }
            else
            {
                String original_title = "";
                String releaseDate = "";
                String overview = "";
                String final_image_url = "";
                String vote_average_txt = "";
                double vote_average =  0.0;
                String movie_id = "";
                ((TextView) rootView.findViewById(R.id.original_title)).setText(original_title);

                ((TextView) rootView.findViewById(R.id.releaseDate)).setText(releaseDate);

                ImageView iconView = (ImageView) rootView.findViewById(R.id.final_image_url);
                Picasso.with(getContext()).load(R.drawable.empty_image).into(iconView);
                //Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w185/weUSwMdQIa3NaXVzwUoIIcAi85d.jpg").into(iconView);
                ((RatingBar) rootView.findViewById(R.id.vote_average)).setNumStars(10);

                //   ((TextView) rootView.findViewById(R.id.movie_rating_text)).setText("Rating:" + (float) vote_average + "/ 10");

                ((RatingBar) rootView.findViewById(R.id.vote_average)).setRating((float)vote_average);


                ((TextView) rootView.findViewById(R.id.overview))
                        .setText(overview);

                ((TextView) rootView.findViewById(R.id.ratingresult))
                        .setText(vote_average_txt);

                ((TextView) rootView.findViewById(R.id.movie_id))
                        .setText(movie_id);
           final View button3 = rootView.findViewById(R.id.button3);
                MovieDatabaseHelper moviewDb = new MovieDatabaseHelper(
                        getContext());
                if(moviewDb.isSiteExists(moviewDb.getReadableDatabase(),original_title))
                    button3.setBackgroundColor(0xFFFF0000);

                button3.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                /* DO SOMETHING UPON THE CLICK */

  //Toast.makeText(getContext(),"fav....",Toast.LENGTH_LONG).show();
                                Intent intent = getActivity().getIntent();
                                if(intent!= null ) {
                                    String original_title = intent.getStringExtra("original_title");
                                    String releaseDate = intent.getStringExtra("releaseDate");
                                    String overview = intent.getStringExtra("overview");
                                    String final_image_url = intent.getStringExtra("final_image_url");
                                    String vote_average_txt = intent.getStringExtra("vote_average_txt");
                                    String movie_id_txt = intent.getStringExtra("movie_id_txt");
                                    double vote_average = intent.getDoubleExtra("vote_average",0.0);
                                    //String movie_id = intent.getStringExtra("movie_id");
                                    int movie_id = intent.getIntExtra("movie_id",0);
                                    MovieDatabaseHelper moviewDb = new MovieDatabaseHelper(
                                            getContext());
                                    Movie site = new Movie(final_image_url,original_title,overview,releaseDate,vote_average,vote_average_txt,movie_id, movie_id_txt);
                                    moviewDb.addMovie(site);
                                    button3.setBackgroundColor(0xFFFF0000);
                                }}
                        }
                );


                final View button = rootView.findViewById(R.id.button);
                button.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                /* DO SOMETHING UPON THE CLICK */

                                //Toast.makeText(getContext(),"rating....",Toast.LENGTH_LONG).show();

                                Intent intent = getActivity().getIntent();
                                int movie_id = intent.getIntExtra("movie_id",0);
                                String movie_id_txt = intent.getStringExtra("movie_id_txt");
                                Intent obj = new Intent(getActivity(),ReviewActivity.class).putExtra("movie_id_txt",movie_id_txt);
                                startActivity(obj);
                            }
                        }
                );


                final View button2 = rootView.findViewById(R.id.button2);
                button2.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                /* DO SOMETHING UPON THE CLICK */

                                //Toast.makeText(getContext(),"trailor....",Toast.LENGTH_LONG).show();
                                Intent intent = getActivity().getIntent();
                                int movie_id = intent.getIntExtra("movie_id",0);
                                String movie_id_txt = intent.getStringExtra("movie_id_txt");
                                Intent obj = new Intent(getActivity(),TrailorActivity.class).putExtra("movie_id_txt",movie_id_txt);
                                startActivity(obj);
                            }
                        }
                );
            }






            //}
        }




        return rootView;
    }



}
