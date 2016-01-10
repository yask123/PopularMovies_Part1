package com.example.yasksrivastava.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
            TextView textView = (TextView) rootview.findViewById(R.id.detail_title);
            textView.setText(movietitle);

            TextView img_url = (TextView) rootview.findViewById(R.id.detail_imgurl);
            img_url.setText(movieurl);
        }
        return rootview;
    }
}
