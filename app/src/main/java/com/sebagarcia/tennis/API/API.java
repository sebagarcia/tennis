package com.sebagarcia.tennis.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cristian on 5/29/17.
 */

public class API {
    public static final String BASE_URL= "http://10.11.0.243:3000/api/";
    private static Retrofit retrofit = null;

    public static Retrofit getApi(){
        if(retrofit == null){
             retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


}
