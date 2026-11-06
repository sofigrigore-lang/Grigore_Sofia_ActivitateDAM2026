package com.example.seminar2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private static final String EXTRA_LISTA = "lista";
    private static final String EXTRA_PACIENT = "pacient";

    private final ArrayList<SanatateGenerala> listaSanatate = new ArrayList<>();
    private CustomAdapter adaptor;
    private int pozitieSelectata = -1;

    private final ActivityResultLauncher<Intent> launcherEditare =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    rezultat -> {
                        if (rezultat.getResultCode() == RESULT_OK
                                && rezultat.getData() != null
                                && pozitieSelectata >= 0
                                && pozitieSelectata < listaSanatate.size()) {
                            SanatateGenerala modificat = rezultat.getData()
                                    .getParcelableExtra(EXTRA_PACIENT);
                            if (modificat != null) {
                                SanatateGenerala selectat = listaSanatate.get(pozitieSelectata);
                                selectat.nume = modificat.nume;
                                selectat.prenume = modificat.prenume;
                                selectat.varsta = modificat.varsta;
                                selectat.sex = modificat.sex;
                                selectat.dataCalendaristica = modificat.dataCalendaristica;
                                selectat.bolnav = modificat.bolnav;
                                selectat.urgent = modificat.urgent;
                                adaptor.notifyDataSetChanged();
                            }
                        }
                        pozitieSelectata = -1;
                    }
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ArrayList<SanatateGenerala> listaPrimita = getIntent()
                .getParcelableArrayListExtra(EXTRA_LISTA);
        if (listaPrimita != null) {
            listaSanatate.addAll(listaPrimita);
        }

        ListView listView = findViewById(R.id.listViewGradini);
        adaptor = new CustomAdapter(this, R.layout.elem_listview,
                listaSanatate, getLayoutInflater());
        listView.setAdapter(adaptor);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pozitieSelectata = position;
                Intent intent = new Intent(ListActivity.this, AddActivity.class);
                intent.putExtra(AddActivity.EXTRA_EDIT, listaSanatate.get(position));
                launcherEditare.launch(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                listaSanatate.remove(position);
                adaptor.notifyDataSetChanged();
                return true;
            }
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent rezultat = new Intent();
                rezultat.putParcelableArrayListExtra(EXTRA_LISTA, listaSanatate);
                setResult(RESULT_OK, rezultat);
                finish();
            }
        });
    }
}
