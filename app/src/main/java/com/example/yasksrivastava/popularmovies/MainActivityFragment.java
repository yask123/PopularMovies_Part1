package com.example.yasksrivastava.popularmovies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
    ListView listview;
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_main, container, false);

        PopularMovie movies_data[]={new PopularMovie("SpierMan","http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg")
        };

        movieAdapter = new MovieAdapter(getActivity(), Arrays.asList(movies_data));

        listview = (ListView)rootview.findViewById(R.id.movie_listview);
        listview.setAdapter(movieAdapter);
        FetchMovieData movieTask = new FetchMovieData();
        movieTask.execute();

        return rootview;
    }

    public class FetchMovieData extends AsyncTask<Void,Void,PopularMovie[]>{

        @Override
        protected void onPostExecute(PopularMovie[] finalresult) {
            Log.v("on POST EXEC","Running");
            movieAdapter = new MovieAdapter(getActivity(), Arrays.asList(finalresult));
            movieAdapter.notifyDataSetChanged();
            listview.setAdapter(movieAdapter);
            Log.v("TESSS",finalresult[0].imgurl);
        }

        @Override
        protected PopularMovie[] doInBackground(Void... params ){
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String forecastJsonStr = null;
            PopularMovie result[] = new PopularMovie[20];
            try {

                URL url = new URL("http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=05df3644ee3e75b407700ca976b07874");

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
                        Log.v("not final",getresults.getJSONObject(i).getString("original_title"));
                        result[i]= new PopularMovie(getresults.getJSONObject(i).getString("original_title"),"http://image.tmdb.org/t/p/w185/"+getresults.getJSONObject(i).getString("poster_path"));

                    }
                    Log.v("Return Result",result[0].title);
                    return  result;
                }catch (JSONException e){
                Log.e("JSON :","JSON ERROR!");
                }

            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                return  null;
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