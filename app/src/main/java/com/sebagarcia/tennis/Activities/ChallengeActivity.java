package com.sebagarcia.tennis.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
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

public class ChallengeActivity extends AppCompatActivity {
    private ArrayList<String> data = new ArrayList<String>();
    private SharedPreferences pref;
    private Player player;
    private TextView list_players;
    private Button btn_gane;
    private Button btn_perdi;

    private ListView listview;
    private List<Player> players;
    ArrayList<HashMap<String, String>> empresaList;

    private static final String TAG_ID = "id";
    private static final String TAG_NOMBRE = "nombre";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        setContentView(R.layout.activity_main);
        bindUI();
        loadPlayers(players);
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

                    map.put(TAG_ID, player.getId());
                    map.put(TAG_NOMBRE, player.getName());
                    empresaList.add(map);
                    data.add(player.getName());
                }
                ListAdapter adapter = new SimpleAdapter(
                        ChallengeActivity.this,
                        empresaList,
                        R.layout.activity_challenge,
                        new String[] {
                                TAG_NOMBRE
                        },
                        new int[] {
                                R.id.list_players
                        });
                listview.setAdapter(adapter);

            }


            @Override
            public void onFailure(Call<List<Player>> call, Throwable t) {
                Toast.makeText(ChallengeActivity.this, "Error en la API", Toast.LENGTH_LONG).show();
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

    private class MyListAdaper extends ArrayAdapter<String> {
        private int layout;
        private List<String> mObjects;
        private MyListAdaper(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            mObjects = objects;
            layout = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder mainViewholder = null;
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.title = (TextView) convertView.findViewById(R.id.list_players);
                viewHolder.button1 = (Button) convertView.findViewById(R.id.btn_gane);
                viewHolder.button2 = (Button) convertView.findViewById(R.id.btn_perdi);
                convertView.setTag(viewHolder);
            }
            mainViewholder = (ViewHolder) convertView.getTag();
            mainViewholder.button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Button was clicked for list item " + position, Toast.LENGTH_SHORT).show();
                }
            });
            mainViewholder.button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Button was clicked for list item " + position, Toast.LENGTH_SHORT).show();
                }
            });
            mainViewholder.title.setText(getItem(position));

            return convertView;
        }
    }
    public class ViewHolder {

        Button button1;
        TextView title;
        Button button2;
    }
    private void removeSharedPreferences(){
        pref.edit().clear().apply();
    }
}
