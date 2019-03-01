package com.ftninformatika.vezbewebservisi.activities;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ftninformatika.vezbewebservisi.R;
import com.ftninformatika.vezbewebservisi.net.MyService;
import com.ftninformatika.vezbewebservisi.net.model.TheRealMovie;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailActivity extends AppCompatActivity {

    private boolean isImageFitToScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final String id = getIntent().getExtras().getString("id");
        Map<String, String> query = new HashMap<String, String>();
        query.put("apikey", "72ccd27b");
        query.put("i", id);
        query.put("plot", "full");

        Call<TheRealMovie> call = MyService.apiInterface().realMovie(query);
        call.enqueue(new Callback<TheRealMovie>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<TheRealMovie> call, Response<TheRealMovie> response) {
                if (response.code() == 200) {
                    TheRealMovie realMovies = response.body();

                    TextView textView = findViewById(R.id.detail_textview);
                    textView.setText("Naziv Filma" + realMovies.getTitle());

                    TextView zanr = findViewById(R.id.detail_zanr);
                    zanr.setText("Zanr: " + realMovies.getGenre());

                    TextView godProizvodnjeFilma = findViewById(R.id.detail_godinaFilma);
                    godProizvodnjeFilma.setText("Film napravljen: " + realMovies.getYear());

                    TextView godPustenFilm = findViewById(R.id.detail_Released);
                    godPustenFilm.setText("Objavljen: " + realMovies.getReleased());

                    TextView website = findViewById(R.id.website);
                    website.setText("Link od Filma: " + realMovies.getWebsite());

                    TextView ocenaTX = findViewById(R.id.text_ocenaRating);
                    ocenaTX.setText("Ocena Filma: ");

                    RatingBar rating = findViewById(R.id.rating);
                    rating.setRating(realMovies.getImdbRating());

                    TextView textGlumci = findViewById(R.id.text_Glumci);
                    textGlumci.setText("Glavni Glumci: ");

                    final ListView listView = findViewById(R.id.lista_glumci);
                    List<String> listaGlumaca = Collections.singletonList(realMovies.getActors());
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(DetailActivity.this, android.R.layout.simple_list_item_1, listaGlumaca);
                    listView.setAdapter(adapter);

                    final ImageView imageView = findViewById(R.id.imageView);
                    Picasso.with(DetailActivity.this)
                            .load(realMovies.getPoster())
                            .into(imageView);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (isImageFitToScreen) {
                                isImageFitToScreen = false;
                                imageView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                                imageView.setAdjustViewBounds(true);
                            } else {
                                isImageFitToScreen = true;
                                imageView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
                                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                            }

                        }
                    });


                }

            }

            @Override
            public void onFailure(Call<TheRealMovie> call, Throwable t) {


            }

        });


    }

}
