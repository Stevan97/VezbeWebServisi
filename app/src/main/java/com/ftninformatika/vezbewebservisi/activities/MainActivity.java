package com.ftninformatika.vezbewebservisi.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.ftninformatika.vezbewebservisi.R;
import com.ftninformatika.vezbewebservisi.net.MyService;
import com.ftninformatika.vezbewebservisi.net.model.Movies;
import com.ftninformatika.vezbewebservisi.net.model.Search;

import java.util.ArrayList;
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

    private void getMovieName(final String text) {


        Map<String, String> query = new HashMap<String, String>();
        query.put("apikey", "72ccd27b");
        query.put("s", text);

        Call<Movies> call = MyService.apiInterface().searchMovies(query);
        call.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {

                if (response.code() == 200) {

                    Movies movies = response.body();
                    final List<Search> listaFilmova = movies.getSearch();

                    /** Provera kad se ukuca film koji ne postoji (ili se ne ukuca nista) da ne pukne program
                     * ++ da osvezava listu, da npr ako ukucamo film koji postoji i prikaze listu i
                     * sledeci put kad ukucamo neki film koji ne postoji da prikaze praznu listu.*/
                    if (response.body().getSearch() == null) {

                        ListView listView = findViewById(R.id.listViewMain);
                        List<Search> lista = new ArrayList<>();
                        ArrayAdapter<Search> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, lista);
                        listView.setAdapter(adapter);

                        Toast.makeText(MainActivity.this, "DOEST NOT EXIST", Toast.LENGTH_LONG).show();
                        return;

                    }




                    final ListView listView = findViewById(R.id.listViewMain);
                    ArrayAdapter<Search> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, listaFilmova);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                            Search s = (Search) listView.getItemAtPosition(position);

                            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                            intent.putExtra("id", s.getImdbID());
                            startActivity(intent);

                        }
                    });

                }
            }


            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Nema Filma po tom imenu", Toast.LENGTH_LONG).show();
            }
        });

    }

}
