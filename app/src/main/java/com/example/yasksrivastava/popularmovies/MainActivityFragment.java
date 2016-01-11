package com.example.yasksrivastava.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
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
import java.util.Arrays;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    private MovieAdapter movieAdapter;
    GridView gridview;
    public MainActivityFragment() {
    }

    @Override
    public void onStart(){

        super.onStart();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String order = prefs.getString("sort_order", "");
        Log.v("Selected settings = ", order);
        FetchMovieData movieTask = new FetchMovieData();
        movieTask.execute(order);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_main, container, false);

        PopularMovie movies_data[]={};

        movieAdapter = new MovieAdapter(getActivity(), Arrays.asList(movies_data));

        gridview = (GridView)rootview.findViewById(R.id.movie_listview);
        gridview.setAdapter(movieAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                PopularMovie detail_movie = movieAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), Main2Activity.class);
                Bundle extras = new Bundle();
                extras.putString("detailTitle", detail_movie.title);
                extras.putString("detaiImgurl", detail_movie.imgurl);
                extras.putString("detaildate",detail_movie.releaseDate);
                extras.putString("detailvotes",String.valueOf(detail_movie.vote_avg));
                extras.putString("detailsummary",detail_movie.overview);
                intent.putExtras(extras);
                startActivity(intent);

            }
        });


        return rootview;
    }

    public class FetchMovieData extends AsyncTask<String,Void,PopularMovie[]>{

        @Override
        protected void onPostExecute(PopularMovie[] finalresult) {
            movieAdapter = new MovieAdapter(getActivity(), Arrays.asList(finalresult));
            movieAdapter.notifyDataSetChanged();
            gridview.setAdapter(movieAdapter);
        }

        @Override
        protected PopularMovie[] doInBackground(String... params ){
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String forecastJsonStr = null;
            PopularMovie result[] = new PopularMovie[20];
            try {
                //vote_count
                String selected_option = params[0];
                if (selected_option.equals("popular")){
                    selected_option="popularity";
                }
                else{
                    selected_option="vote_count";
                }
                final String BASE_URL = "http://api.themoviedb.org/3/discover/movie";
                final  String key = "";

                Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                        .appendQueryParameter("sort_by", selected_option+".desc")
                        .appendQueryParameter("api_key", key)
                        .build();
                Log.e("Url may be ",builtUri.toString());
                URL url = new URL(builtUri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return  null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }
                forecastJsonStr = buffer.toString();

                try {
                    JSONObject moviejson = new JSONObject(forecastJsonStr);
                    JSONArray getresults = moviejson.getJSONArray("results");

                    for(int i = 0;i<getresults.length();i++){


                        String temp_title = getresults.getJSONObject(i).getString("original_title");
                        String temp_imgurl = "http://image.tmdb.org/t/p/w185/"+getresults.getJSONObject(i).getString("poster_path");
                        String temp_release_data=getresults.getJSONObject(i).getString("release_date");
                        double temp_voteavg = getresults.getJSONObject(i).getDouble("vote_average");
                        String temp_overview = getresults.getJSONObject(i).getString("overview");
                        result[i]= new PopularMovie(temp_title,temp_imgurl,temp_release_data,temp_voteavg,temp_overview);

                        Log.v("not final",temp_imgurl);

                    }
                    Log.v("Return Result",result[0].title);
                    return  result;
                }catch (JSONException e){
                Log.e("JSON :","JSON ERROR!");
                }

            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                return  result;
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
                return result;
            }
        }
    }
}
