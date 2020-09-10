package fr.ay.moviez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView go ;
    private static final String TAG = "MyActivity";
    String string = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.go = findViewById(R.id.go);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent otherActivity= new Intent(getApplicationContext(), MovieActivity.class);
                Log.e(TAG, string);

                startActivity(otherActivity);
                finish();

            }
        });
    }
}