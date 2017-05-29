package com.sebagarcia.tennis.Splash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.sebagarcia.tennis.Activities.LoginActivity;
import com.sebagarcia.tennis.Activities.RankingActivity;
import com.sebagarcia.tennis.Util.Util;


import org.w3c.dom.Text;

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        pref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        Intent intentLogin = new Intent(this, LoginActivity.class);
        Intent intentMain = new Intent(this, RankingActivity.class);

        if (!TextUtils.isEmpty(Util.getUserMailPref(pref))){
            startActivity(intentMain);
        }else{
            startActivity(intentLogin);
        }
        finish();
    }
}
