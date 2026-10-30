package com.example.seminar2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private static final String EXTRA_DATE = "date";
    private static final String EXTRA_LISTA = "lista";

    private final ArrayList<SanatateGenerala> listaSanatate =
            new ArrayList<>();

    private ArrayAdapter<SanatateGenerala> adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ListView listView = findViewById(R.id.listViewGradini);

        Bundle pachet = getIntent().getBundleExtra(EXTRA_DATE);

        if (pachet != null) {
            @SuppressWarnings("unchecked")
            ArrayList<SanatateGenerala> listaPrimita =
                    (ArrayList<SanatateGenerala>)
                            pachet.getSerializable(EXTRA_LISTA);

            if (listaPrimita != null) {
                listaSanatate.addAll(listaPrimita);
            }
        }

        adaptor = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                listaSanatate
        );

        listView.setAdapter(adaptor);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(
                            AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {

                        SanatateGenerala persoana =
                                listaSanatate.get(position);

                        Toast.makeText(
                                ListActivity.this,
                                persoana.toString(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        );

        listView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(
                            AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {

                        listaSanatate.remove(position);
                        adaptor.notifyDataSetChanged();

                        return true;
                    }
                }
        );

        getOnBackPressedDispatcher().addCallback(
                this,
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        Intent rezultat = new Intent();

                        Bundle pachetRezultat = new Bundle();
                        pachetRezultat.putSerializable(
                                EXTRA_LISTA,
                                listaSanatate
                        );

                        rezultat.putExtra(EXTRA_DATE, pachetRezultat);
                        setResult(RESULT_OK, rezultat);
                        finish();
                    }
                }
        );
    }
}