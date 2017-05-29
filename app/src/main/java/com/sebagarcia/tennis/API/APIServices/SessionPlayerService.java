package com.sebagarcia.tennis.API.APIServices;

import com.sebagarcia.tennis.Clases.Player;

import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by cristian on 5/29/17.
 */

public interface SessionPlayerService {

    @Headers({
            "Accept: application/vnd.github.v3.full+json",
            "User-Agent: Retrofit-Sample-App"
    })
    @POST("sessions")
    Call<Player> newSessionPlayer(@Query("email") String sessionEmail,
                                         @Query("password") String sessionPassword);
}
