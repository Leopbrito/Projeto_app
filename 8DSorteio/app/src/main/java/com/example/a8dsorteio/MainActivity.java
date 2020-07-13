package com.example.a8dsorteio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar();

        final TextView txtR1, txtR2, txtP1, txtP2;
        Button btSortear;

        txtR1 = findViewById(R.id.txtR1);
        txtR2 = findViewById(R.id.txtR2);
        txtP1 = findViewById(R.id.txtP1);
        txtP2 = findViewById(R.id.txtP2);
        btSortear = findViewById(R.id.btSortear);

        btSortear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();

                int r1 = random.nextInt(5);
                int p1 = random.nextInt(5);
                while (p1 == r1){
                    p1 = random.nextInt(5);
                }

                int r2 = random.nextInt(5);
                int p2 = random.nextInt(5);

                while (r1 == r2){
                    r2 = random.nextInt(5);
                }

                while (r2 == p2 || p2 == p1){
                    p2 = random.nextInt(5);
                }

                txtR1.setText("Grupo " + Integer.valueOf(r1+1));
                txtP1.setText("Grupo " + Integer.valueOf(p1+1));
                txtR2.setText("Grupo " + Integer.valueOf(r2+1));
                txtP2.setText("Grupo " + Integer.valueOf(p2+1));
            }

        });


    }
}