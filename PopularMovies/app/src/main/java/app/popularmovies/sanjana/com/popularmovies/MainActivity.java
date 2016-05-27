package app.popularmovies.sanjana.com.popularmovies;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private Boolean mTabletMode = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if(findViewById(R.id.container)!= null){
            mTabletMode = true;
            DetailActivityFragment detailFragment = new DetailActivityFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, detailFragment).commit();
        }


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
    }



    public boolean isTablet() {
        return mTabletMode;
    }

    public void replaceFragment(String releaseDate,String original_title, String overview,String final_image_url,
                                double vote_average, String vote_average_txt,int movie_id, String movie_id_txt) {
        Log.i("tab", "replace");
        Bundle bundle = new Bundle();
        bundle.putInt("movie_id", movie_id);
        bundle.putDouble("vote_average",vote_average);
        bundle.putString("releaseDate",releaseDate);
        bundle.putString("overview",overview);
        bundle.putString("final_image_url",final_image_url);
        bundle.putString("vote_average_txt",vote_average_txt);
        bundle.putString("movie_id_txt",movie_id_txt);
        bundle.putString("original_title",original_title);
        //fragment.setArguments(bundle);



        DetailActivityFragment detailFragment = new DetailActivityFragment();



        detailFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, detailFragment).commit();
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

        return super.onOptionsItemSelected(item);
    }*/
}
