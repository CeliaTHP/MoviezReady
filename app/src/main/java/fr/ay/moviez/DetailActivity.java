package fr.ay.moviez;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.tabs.TabLayout;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import fr.ay.moviez.ui.main.SectionsPagerAdapter;

public class DetailActivity extends AppCompatActivity {
    TextView movieID;
   ArrayList<VideoList> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //tabs

        //get movie id
        movieID = findViewById(R.id.id_detail);
        Intent details = getIntent();
        String id = details.getStringExtra("id");
        movieID.setText(id);

        //get recycle view
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerView = findViewById(R.id.video_recycleview);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        arrayList = new ArrayList<>();

        VideoList videoList = new VideoList("https://www.youtube.com/watch?v=5lGoQhFb4NM");
        arrayList.add(videoList);
        VideoList videoList1 = new VideoList("https://www.youtube.com/watch?v=V_yI8Tjx_Xo");
        arrayList.add(videoList1);


        VideoAdapter videoAdapter = new VideoAdapter(arrayList,getApplicationContext());
        recyclerView.setAdapter(videoAdapter);









        getDetails();
       // getVideos();


    }



    //getVideos;










    //getDetails();

    private void getDetails() {
        RequestQueue queue = Volley.newRequestQueue(this);

        movieID = findViewById(R.id.id_detail);
        Intent details = getIntent();
        String id = details.getStringExtra("id");
        movieID.setText(id);

        String findUrl = "https://api.themoviedb.org/3/movie/ " + id + "?api_key=e4a9d54204f8ee1d8121e867e9a8a5a5";
        final String baseurl = "https://image.tmdb.org/t/p/w500";


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, findUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                for (int i = 0; i < response.length(); i++) {
                    try {

                        String title = response.getString("title");
                        TextView dtitle = findViewById(R.id.title_detail);
                        dtitle.setText(title);

                        String overview = response.getString("overview");
                        TextView dsyn = findViewById(R.id.syn_detail);
                        dsyn.setText(overview);

                        String date = response.getString("release_date");
                        TextView ddate = findViewById(R.id.date_detail);
                        ddate.setText(date);


                        String poster = response.getString("backdrop_path");
                        ImageView dposter = findViewById(R.id.poster_detail);
                        Picasso.get().load(baseurl+poster).into(dposter);


                        //GetGenres

                        JSONArray genres = response.getJSONArray("genres");
                        ArrayList list = new ArrayList();



                        for (int x = 0; x < genres.length(); x++) {
                        JSONObject gen = genres.getJSONObject(x);
                        TextView dgen = findViewById(R.id.genre_detail);

                        String genv = gen.getString("name");
                        list.add(genv);
                        dgen.setText(list.toString().replace("["," ").replace("]"," ").trim());
                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

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


