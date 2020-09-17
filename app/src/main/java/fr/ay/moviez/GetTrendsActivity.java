package fr.ay.moviez;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetTrendsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {




    //basic
    RecyclerView recyclerView;
    List<Movie> movieList;

    int spinner_pos;



    MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        //spinner

        Spinner spinner = findViewById(R.id.selection_spinner);
        ArrayAdapter<CharSequence> spinner_adapter = ArrayAdapter.createFromResource(this,R.array.spinner_array,R.layout.support_simple_spinner_dropdown_item);
        spinner_adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(spinner_adapter);
        spinner.setOnItemSelectedListener(this);


        //trends
        recyclerView = findViewById(R.id.list_recycler_view_movie);
        RecyclerView.ItemDecoration div = new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(div);
        movieList = new ArrayList<>();

        getTrends();




    }




    private void getTrends() {
        String url = "https://api.themoviedb.org/3/trending/all/day?api_key=e4a9d54204f8ee1d8121e867e9a8a5a5";
        final String baseurl = "http://image.tmdb.org/t/p/w154";

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



    //spinner
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {



         if(i == 1) {
            Intent otherActivity = new Intent(getApplicationContext(), GetAllActivity.class);
            startActivity(otherActivity);
            finish();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {


    }

}

