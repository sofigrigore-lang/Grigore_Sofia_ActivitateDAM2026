package com.example.seminar2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnAdauga, btnAdaugaAI;
    TextView textRezultat;

    ActivityResultLauncher<Intent> launcher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {

                        if (result.getResultCode() == RESULT_OK &&
                                result.getData() != null) {

                            Bundle date = result.getData()
                                    .getBundleExtra("date");

                            if (date != null) {
                                SanatateGenerala pacient =
                                        (SanatateGenerala)
                                                date.getSerializable("pacient");

                                if (pacient != null) {
                                    textRezultat.setText(
                                            pacient.afisare()
                                    );
                                }
                            }
                        }
                    }
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdauga = findViewById(R.id.btnAdauga);
        btnAdaugaAI = findViewById(R.id.btnAdaugaAI);
        textRezultat = findViewById(R.id.textRezultat);

        btnAdauga.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddActivity.class);
            launcher.launch(intent);
        });

        btnAdaugaAI.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddAIActivity.class);
            launcher.launch(intent);
        });
    }
}