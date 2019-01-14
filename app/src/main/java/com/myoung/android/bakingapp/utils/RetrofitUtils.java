package com.myoung.android.bakingapp.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtils {
    // Constants
    // https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json
    private static final String URL
            = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/";


    // Variables
    private static final Object LOCK = new Object();
    private static RetrofitUtils instance;
    private final Retrofit retrofit;
    private final RetrofitService service;


    // Constructor
    private RetrofitUtils() {
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(RetrofitService.class);
    }


    public static RetrofitUtils getInstance() {
        if(instance == null) {
            synchronized (LOCK) {
                instance = new RetrofitUtils();
            }
        }
        return instance;
    }

    public RetrofitService getService() {
        return service;
    }
}
