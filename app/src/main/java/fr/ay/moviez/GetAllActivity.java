package fr.ay.moviez;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class GetAllActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //basic
    RecyclerView recyclerView;
    List<Movie> movieList;

    MovieAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all);


        //spinner

        Spinner spinner = findViewById(R.id.selection_spinner2);
        ArrayAdapter<CharSequence> spinner_adapter = ArrayAdapter.createFromResource(this, R.array.spinner_array_all, R.layout.support_simple_spinner_dropdown_item);
        spinner_adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(spinner_adapter);
        spinner.setOnItemSelectedListener(this);

        //trends
        recyclerView = findViewById(R.id.all_movie_recycle_view);
        RecyclerView.ItemDecoration div = new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(div);
        movieList = new ArrayList<>();

        getAll(500);





    }


    public void getAll(int page){

        TextView pagenumview = findViewById(R.id.pagenum);
        pagenumview.setText(page + "/500");

        String choice = "popularity.desc";

        String url = "https://api.themoviedb.org/3/discover/movie?api_key=e4a9d54204f8ee1d8121e867e9a8a5a5&language=en-US&sort_by=" + choice + "&include_adult=false&include_video=false&page=" + page;
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


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if (i == 1) {
            Intent otherActivity = new Intent(getApplicationContext(), GetTrendsActivity.class);
            startActivity(otherActivity);
            finish();
            return;
        }
    }

        @Override
        public void onNothingSelected (AdapterView < ? > adapterView){

        }


}