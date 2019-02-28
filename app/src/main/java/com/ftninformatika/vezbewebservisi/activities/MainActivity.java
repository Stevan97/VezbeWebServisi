package com.ftninformatika.vezbewebservisi.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.ftninformatika.vezbewebservisi.R;
import com.ftninformatika.vezbewebservisi.net.MyService;
import com.ftninformatika.vezbewebservisi.net.model.Movies;
import com.ftninformatika.vezbewebservisi.net.model.Search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonSearch = findViewById(R.id.button_searchMovies);
        final EditText editMovieSearch = findViewById(R.id.edit_text_input);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMovieName(editMovieSearch.getText().toString());
            }
        });

    }

    private void getMovieName(String text) {


        Map<String, String> query = new HashMap<String, String>();
        query.put("apikey", "72ccd27b");
        query.put("s", text);

        Call<Movies> call = MyService.apiInterface().searchMovies(query);
        call.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {

                List<String> listaFilmova = new ArrayList<>();

                if (response.code() == 200) {
                    Movies movies = response.body();

                    for (final Search s : movies.getSearch()) {
                        //s.getTitle();

                        Log.i("Search", s.getTitle());


                        listaFilmova.add(s.getTitle());

                        ListView listView = findViewById(R.id.listViewMain);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, listaFilmova);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                //   Movies moviesPosition = (Movies) listView.getItemAtPosition(position);
                                Map<String, String> queryDetail = new HashMap<>();
                                queryDetail.put("apikey", "72ccd27b");
                                queryDetail.put("i", s.getImdbID());

                                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                                intent.putExtra("Query", s.getImdbID());
                                startActivity(intent);
                            }
                        });

                    }


                }

            }


            @Override
            public void onFailure(Call<Movies> call, Throwable t) {

            }
        });


    }

}
