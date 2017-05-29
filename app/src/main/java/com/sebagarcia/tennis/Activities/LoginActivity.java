package com.sebagarcia.tennis.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.sebagarcia.tennis.API.API;
import com.sebagarcia.tennis.Clases.Player;
import com.sebagarcia.tennis.R;
import com.sebagarcia.tennis.API.APIServices.SessionPlayerService;
import com.sebagarcia.tennis.Util.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences pref;

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Switch  switchRemember;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        bindUI();
        setCredentialsIsExist();
    }

    public void onClickButtonLogin(View view){
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if(login(email,password)){
            Retrofit retrofit = API.getApi();
            SessionPlayerService service = retrofit.create(SessionPlayerService.class);
            Call<Player> playerCall = service.newSessionPlayer(email,password);
            playerCall.enqueue(new Callback<Player>() {
                @Override
                public void onResponse(Call<Player> call, Response<Player> response) {
                    Player player = response.body();
                    if (player != null){
                        goToMain();
                        saveOnPreferences(player.getEmail(),player.getId(),player.getName());
                    }else{
                        Toast.makeText(LoginActivity.this,"Email y/o Contraseña inválidos",
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


    private boolean login(String email, String password){
        boolean valid=false;
        if (!isValidEmail(email)){
            Toast.makeText(this,"El email no es válido, por favor intentar de nuevo",Toast.LENGTH_LONG).show();
        }else if (!isValidPassword(password)){
            Toast.makeText(this,"La contraseña no es válida, por favor intentar de nuevo",Toast.LENGTH_LONG).show();
        }else{
           valid = true;
        }
       return valid;
    }

    private void bindUI(){
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        switchRemember = (Switch) findViewById(R.id.switchRemember);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
    }

    private void setCredentialsIsExist(){
        String email = Util.getUserMailPref(pref);
        String password = Util.getUserPassPref(pref);

        if (!TextUtils.isEmpty(email)  && !TextUtils.isEmpty(password)){
            editTextEmail.setText(email);
            editTextPassword.setText(password);
        }
    }

    private boolean isValidEmail(String email){
        boolean vacio = TextUtils.isEmpty(email); //Ver si está vacio
        boolean patrones = Patterns.EMAIL_ADDRESS.matcher(email).matches(); // ver si están los patrones de email (@,.com,etc)
        return  (!vacio && patrones);
    }

    private boolean isValidPassword(String password){
        boolean character  = password.length() > 4 ;
        return character;
    }

    private void saveOnPreferences(String email, int id, String name){

        if (switchRemember.isChecked()){
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("Email",email);
            editor.putInt("Id",id);
            editor.putString("Name",name);
            Toast.makeText(this,"Bienvenido "+name.toString(), Toast.LENGTH_LONG).show();
            editor.apply();     //asincrona
            // editor.commit(); //síncrona
        }
    }

    private void goToMain(){
        //vamos del login activity al main activity
        Intent intent = new Intent(this, RankingActivity.class);

        //Si queremos que no vaya atrás porque es un login tenemos que hacer
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
}
