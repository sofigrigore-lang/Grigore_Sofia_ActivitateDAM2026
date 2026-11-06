package com.example.seminar2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String EXTRA_PACIENT = "pacient";
    private static final String EXTRA_LISTA = "lista";

    private final ArrayList<SanatateGenerala> listaObiecte = new ArrayList<>();
    private TextView textRezultat;

    private final ActivityResultLauncher<Intent> launcherAdaugare =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    rezultat -> {
                        if (rezultat.getResultCode() == RESULT_OK
                                && rezultat.getData() != null) {
                            SanatateGenerala pacient = rezultat.getData()
                                    .getParcelableExtra(EXTRA_PACIENT);
                            if (pacient != null) {
                                listaObiecte.add(pacient);
                                textRezultat.setText(pacient.afisare());
                                deschideLista();
                            }
                        }
                    }
            );

    private final ActivityResultLauncher<Intent> launcherLista =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    rezultat -> {
                        if (rezultat.getResultCode() == RESULT_OK
                                && rezultat.getData() != null) {
                            ArrayList<SanatateGenerala> listaPrimita = rezultat.getData()
                                    .getParcelableArrayListExtra(EXTRA_LISTA);
                            if (listaPrimita != null) {
                                listaObiecte.clear();
                                listaObiecte.addAll(listaPrimita);
                                if (!listaObiecte.isEmpty()) {
                                    textRezultat.setText(listaObiecte.get(
                                            listaObiecte.size() - 1).afisare());
                                }
                            }
                        }
                    }
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAdauga = findViewById(R.id.btnAdauga);
        Button btnAdaugaAI = findViewById(R.id.btnAdaugaAI);
        textRezultat = findViewById(R.id.textRezultat);

        btnAdauga.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            launcherAdaugare.launch(intent);
        });

        btnAdaugaAI.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddAIActivity.class);
            launcherAdaugare.launch(intent);
        });
    }

    private void deschideLista() {
        Intent intent = new Intent(MainActivity.this, ListActivity.class);
        intent.putParcelableArrayListExtra(EXTRA_LISTA, listaObiecte);
        launcherLista.launch(intent);
    }
}
