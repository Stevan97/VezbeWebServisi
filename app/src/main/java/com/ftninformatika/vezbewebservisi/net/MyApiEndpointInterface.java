package com.ftninformatika.vezbewebservisi.net;

import com.ftninformatika.vezbewebservisi.net.model.Movies;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface MyApiEndpointInterface {


    @GET("/")
    Call<Movies> searchMovies(@QueryMap Map<String, String> options);

}
