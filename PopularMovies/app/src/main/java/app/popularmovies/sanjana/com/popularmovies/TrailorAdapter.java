package app.popularmovies.sanjana.com.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by nafiurrashid on 5/24/16.
 */
public class TrailorAdapter extends ArrayAdapter<Trailor> {




    private Context context;
    private LayoutInflater inflater;

    //public ImageAdapter(Context c) {
    //    mContext = c;
    //}


    public TrailorAdapter(Context context, ArrayList<Trailor> movies) {
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
            Trailor movie = (Trailor) getItem(position);

            TextView textView = (TextView) gridView
                    .findViewById(R.id.grid_item_author);
            textView.setText("Click here to watch the trailor:(key) "+movie.key);

            TextView textView2 = (TextView) gridView
                    .findViewById(R.id.grid_item_comment);
            textView2.setText("Site: "+movie.site);

        }




        else {
            gridView = (View) convertView;
        }

        return gridView;
    }


}