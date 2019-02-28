package com.ftninformatika.vezbewebservisi.net;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyService {

    public static Retrofit getReftrofitInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyServiceContract.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

                return retrofit;
    }

    public static MyApiEndpointInterface apiInterface(){

        MyApiEndpointInterface apiService = getReftrofitInstance().create(MyApiEndpointInterface.class);

        return apiService;
    }

}
