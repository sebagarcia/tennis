package com.sebagarcia.tennis.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.sebagarcia.tennis.API.API;
import com.sebagarcia.tennis.API.APIServices.PlayerService;
import com.sebagarcia.tennis.API.APIServices.SessionPlayerService;
import com.sebagarcia.tennis.Clases.Player;
import com.sebagarcia.tennis.R;
import com.sebagarcia.tennis.Util.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MiPerfilActivity extends AppCompatActivity {

    private SharedPreferences pref;

    private EditText editTextName;
    private EditText editTextPhone;
    private EditText editTextMail;
    private EditText editTextRanking;
    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        setContentView(R.layout.activity_mi_perfil);
        bindUI();
        loadPlayer();


    }


    private void bindUI(){
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextName.setEnabled(false);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextPhone.setEnabled(false);
        editTextMail = (EditText) findViewById(R.id.editTextMail);
        editTextMail.setEnabled(false);
        editTextRanking = (EditText) findViewById(R.id.editTextRanking);
        editTextRanking.setEnabled(false);
    }

    private void setUI(Player player){
        editTextName.setText(player.getName());
        editTextMail.setText(player.getEmail());
        editTextPhone.setText(player.getPhone());
        editTextRanking.setText("Ranking "+String.valueOf(player.getScore()));
    }

    private void loadPlayer(){
        Retrofit retrofit = API.getApi();
        PlayerService service = retrofit.create(PlayerService.class);
        Call<Player> playerCall = service.getPlayer(Util.getUserIdPref(pref));
        playerCall.enqueue(new Callback<Player>() {
            @Override
            public void onResponse(Call<Player> call, Response<Player> response) {
                Player player = response.body();
                if (player != null){
                    setUI(player);
                }else{
                    Toast.makeText(MiPerfilActivity.this,"Problemas con la consulta",
                            Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Player> call, Throwable t) {
                System.out.print(true);
            }
        });
    }


}
