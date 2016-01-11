package com.example.yasksrivastava.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class Main2ActivityFragment extends Fragment {

    public Main2ActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_main2, container, false);

        

        Intent intent = getActivity().getIntent();
        Bundle extra = intent.getExtras();
        extra.getString("detailTitle");
        if (intent != null ) {

            String movietitle = extra.getString("detailTitle");
            String movieurl = extra.getString("detaiImgurl");
            String date = extra.getString("detaildate");
            String votes = extra.getString("detailvotes");
            String summary = extra.getString("detailsummary");

            TextView dateView = (TextView) rootview.findViewById(R.id.date);
            dateView.setText(date);

            TextView voteView = (TextView) rootview.findViewById(R.id.votes);
            voteView.setText(votes);

            TextView textView = (TextView) rootview.findViewById(R.id.detail_title);
            textView.setText(movietitle);


            TextView summaryview = (TextView) rootview.findViewById(R.id.summary);
            summaryview.setText(summary);

            ImageView img_poster = (ImageView)rootview.findViewById(R.id.movie_poster);
            Picasso.with(getContext()).load(movieurl).into(img_poster);

        }
        return rootview;
    }
}
