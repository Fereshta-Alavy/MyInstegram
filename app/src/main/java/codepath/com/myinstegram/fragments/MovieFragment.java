package codepath.com.myinstegram.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import codepath.com.myinstegram.R;
import codepath.com.myinstegram.adapters.MovieAdapter;
import codepath.com.myinstegram.models.Movie;
import okhttp3.Headers;

public class MovieFragment extends Fragment {
    private RecyclerView rvMovies;
    MovieAdapter adapter;
    List<Movie> movies;
//    SwipeRefreshLayout swipeLayout;

    public static final String NOW_PLATING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    public static final String TAG = "MovieFragment";


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvMovies = view.findViewById(R.id.rvMovies);

        movies = new ArrayList<>();

        adapter = new MovieAdapter(getContext(), movies);
        rvMovies.setAdapter(adapter);
        rvMovies.setLayoutManager(new LinearLayoutManager(getContext()));



        AsyncHttpClient client = new AsyncHttpClient();

        client.get(NOW_PLATING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG,"onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray result = jsonObject.getJSONArray("results");
                    Log.i(TAG, "results: "+ result.toString());
                    movies.addAll(Movie.fromJsonArray(result));
                    adapter.notifyDataSetChanged();
                    Log.i(TAG, "movies: "+ MovieFragment.this.movies.size());
                } catch (JSONException e) {
                    Log.e(TAG,"hit json exception");
                }


            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");

            }
        });
    }

}
