package app.popularmovies.sanjana.com.popularmovies;

/**
 * Created by sanjana on 4/14/16.
 *
 *
 *

 original title
 movie poster image thumbnail
 A plot synopsis (called overview in the api)
 user rating (called vote_average in the api)
 release date
 */




public class Movie {
    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public void setVote_average_txt(String vote_average_txt) {
        this.vote_average_txt = vote_average_txt;
    }

    public void setFinal_image_url(String final_image_url) {
        this.final_image_url = final_image_url;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }



    String original_title;
    String vote_average_txt;
    String final_image_url;
    String overview;
    String releaseDate;
    double vote_average;
    String movie_id_txt;
    int movie_id;

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }
    public void setMovie_id_txt(String movie_id_txt) {
        this.movie_id_txt = movie_id_txt;
    }

public Movie()
{}

    public Movie(String final_image_url, String original_title, String overview, String releaseDate, double vote_average, String vote_average_txt, int movie_id, String movie_id_txt){
        this.final_image_url = final_image_url;
        this.original_title = original_title;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.vote_average = vote_average;
        this.vote_average_txt = vote_average_txt;
        this.movie_id = movie_id;
        this.movie_id_txt = movie_id_txt;


    }
}
