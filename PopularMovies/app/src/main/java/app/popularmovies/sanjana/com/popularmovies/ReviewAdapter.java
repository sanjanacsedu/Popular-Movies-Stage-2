package app.popularmovies.sanjana.com.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sanjana on 4/12/16.
 */
//public class ImageAdapter extends BaseAdapter {

    //public class ImageAdapter extends BaseAdapter {
public class ReviewAdapter extends ArrayAdapter<Review>{

    private ArrayList<String> imageUrls;


    private Context context;
    private LayoutInflater inflater;

    //public ImageAdapter(Context c) {
    //    mContext = c;
    //}


    public ReviewAdapter(Context context, ArrayList<Review> movies) {
        super(context, R.layout.list_item,movies);

        this.context = context;
        inflater = LayoutInflater.from(context);
    }

/*
    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }
*/
    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView;


        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            //convertView = inflater.inflate(R.layout.list_item, parent, false);

            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.list_item, null);
            Review movie = (Review) getItem(position);

            TextView textView = (TextView) gridView
                    .findViewById(R.id.grid_item_author);
            textView.setText("Author: "+movie.author);

            TextView textView2 = (TextView) gridView
                    .findViewById(R.id.grid_item_comment);
            textView2.setText("Comment: "+movie.content);

        }




        else {
            gridView = (View) convertView;
        }

        return gridView;
    }


}