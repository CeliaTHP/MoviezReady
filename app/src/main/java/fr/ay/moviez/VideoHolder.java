package fr.ay.moviez;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import fr.ay.moviez.R;

public class VideoHolder extends RecyclerView.ViewHolder {
    public WebView webView;
    public View button;


     public VideoHolder(@NonNull View itemView) {
        super(itemView);
        webView = itemView.findViewById(R.id.video_view);
        button = itemView.findViewById(R.id.button_fullscreen);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);


    }
}
