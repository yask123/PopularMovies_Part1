package com.example.yasksrivastava.popularmovies;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class MovieAdapter extends ArrayAdapter<PopularMovie>  {
    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();


    public MovieAdapter(Activity context, List<PopularMovie> popularmovie) {
        super(context, 0, popularmovie);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PopularMovie movie = getItem(position);


        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_layout, parent, false);
        }

        ImageView iconView = (ImageView) convertView.findViewById(R.id.movie_icon);
        try{
            Picasso.with(getContext()).load(movie.imgurl).into(iconView);
        }
        catch (Exception e){
            Log.e("Err", "Cant load images");
        }


        return convertView;
    }
}
