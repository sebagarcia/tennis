package com.sebagarcia.tennis.API.APIServices;

import com.sebagarcia.tennis.Clases.Player;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by cristian on 5/29/17.
 */

public interface PlayerService {

    @Headers({
            "Accept: application/vnd.github.v3.full+json",
            "User-Agent: Retrofit-Sample-App"
    })

    @GET("players")
    Call<List<Player>> getPlayers();

    @GET("players/{id}")
    Call<Player> getPlayer(@Path("id") int playerId);


}
