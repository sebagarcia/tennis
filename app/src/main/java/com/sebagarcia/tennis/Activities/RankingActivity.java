package com.sebagarcia.tennis.Activities;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.sebagarcia.tennis.API.API;
import com.sebagarcia.tennis.API.APIServices.PlayerService;
import com.sebagarcia.tennis.Clases.Player;
import com.sebagarcia.tennis.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RankingActivity extends AppCompatActivity {

    private ListView listview;
    private List<Player> players;
    ArrayList<HashMap<String, String>> empresaList;

    private SharedPreferences pref;

    private static final String TAG_ID = "id";
    private static final String TAG_NOMBRE = "nombre";
    private static final String TAG_PUNTAJE = "puntaje";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        bindUI();
        loadPlayers(players);
        pref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
    }

    private void bindUI(){
        listview = (ListView) findViewById(R.id.listAllProducts);

    }

    private void loadPlayers(final List<Player> players){
        //CARGAMOS LA LISTA DE JUGADORES
        Retrofit retrofit = API.getApi();
        PlayerService service = retrofit.create(PlayerService.class);
        Call<List<Player>> playerCall = service.getPlayers();


        playerCall.enqueue(new Callback<List<Player>>() {
            @Override
            public void onResponse(Call<List<Player>> call, Response<List<Player>> response) {
                List<Player> ps = response.body();
                // Hashmap para el ListView
                empresaList = new ArrayList<HashMap<String, String>>();
                for (Player player:ps){
                    // creating new HashMap
                    HashMap map = new HashMap();

                    // adding each child node to HashMap key => value
                    String puntaje = "Ranking: "+String.valueOf(player.getScore());
                    map.put(TAG_ID, player.getId());
                    map.put(TAG_NOMBRE, player.getName());
                    map.put(TAG_PUNTAJE, puntaje);

                    empresaList.add(map);
                }
                ListAdapter adapter = new SimpleAdapter(
                        RankingActivity.this,
                        empresaList,
                        R.layout.single_post,
                        new String[] {
                                TAG_ID,
                                TAG_NOMBRE,
                                TAG_PUNTAJE,
                        },
                        new int[] {
                                R.id.single_post_tv_id,
                                R.id.single_post_tv_nombre,
                                R.id.single_post_tv_puntaje,
                        });
                // updating listview
                //setListAdapter(adapter);
                listview.setAdapter(adapter);
            }


            @Override
            public void onFailure(Call<List<Player>> call, Throwable t) {
                Toast.makeText(RankingActivity.this, "Error en la API", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.mi_perfil:
                Intent intent = new Intent(this, MiPerfilActivity.class);
                startActivity(intent);
                return true;

            case R.id.desafio:
                intent = new Intent(this, ChallengeActivity.class);
                startActivity(intent);
                return true;

            case R.id.ranking:
                intent = new Intent(this, RankingActivity.class);
                startActivity(intent);
                return true;

            case R.id.menu_forget_logout:
                removeSharedPreferences();
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    private void logout(){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void removeSharedPreferences(){
        pref.edit().clear().apply();
    }

}
