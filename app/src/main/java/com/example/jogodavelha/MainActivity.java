package com.example.jogodavelha;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnNovo)
    public void clickNovo(View view){
        startActivity(new Intent(MainActivity.this, GameActivity.class));

    }

    @OnClick(R.id.btnSobre)
    public void clickSobre(View view){
        startActivity(new Intent(MainActivity.this, Sobre.class));

    }

    @OnClick(R.id.btnSair)
    public void clickSair(View view){
        finish();
        moveTaskToBack(true);

    }

}
