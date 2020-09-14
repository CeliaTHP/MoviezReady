package fr.ay.moviez;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ModuleInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MovieActivity extends AppCompatActivity {

RecyclerView recyclerView;
List<Movie> movieList;
private static String url = "https://api.themoviedb.org/3/trending/all/day?api_key=e4a9d54204f8ee1d8121e867e9a8a5a5";
private static String baseurl = "http://image.tmdb.org/t/p/w154";
MovieAdapter adapter;

//check.Moviexml


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        recyclerView = findViewById(R.id.list_recycler_view_movie);
        RecyclerView.ItemDecoration div = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(div);
        movieList = new ArrayList<>();
        getTrends();


        ImageView imageView = findViewById(R.id.poster_detail);
        TextView movieTitle = findViewById(R.id.title_detail);
        TextView movieDate = findViewById(R.id.date_detail);
        TextView movieGen = findViewById(R.id.genre_detail);
        TextView movieSyn = findViewById(R.id.syn_detail);



    }

    private void getTrends() {

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONArray jsonArray = null;
                try {
                    jsonArray = response.getJSONArray("results");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                    JSONObject res = jsonArray.getJSONObject(i);
                        Movie movie = new Movie();
                        movie.setTitle(res.getString("title"));
                        movie.setDate(res.getString("release_date"));
                        movie.setSyn(res.getString("overview"));
                        movie.setID(res.getString("id"));
                        movie.setImageUrl(baseurl + res.getString("poster_path"));
                        movieList.add(movie);

                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                    }

                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new MovieAdapter(getApplicationContext(),movieList);
                recyclerView.setAdapter(adapter);


                }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }

        });
        queue.add(request);

    }

}