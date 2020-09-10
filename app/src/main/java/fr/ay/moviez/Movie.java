package fr.ay.moviez;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Movie extends  ArrayList{
    private String imageUrl;
    private String title;
    private String date;
    private String syn;
    private String id;

    public Movie() {}
    public Movie(String imageUrl, String title, String date, String syn,String id) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.date = date;
        this.syn = syn;
        this.id = id;

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSyn() { return syn; }

    public void setSyn(String syn) {
        this.syn = syn;
    }

    public String getID() {return id; }

    public void setID(String id) {this.id = id ;}
}

