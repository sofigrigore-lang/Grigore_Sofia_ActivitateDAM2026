package com.example.seminar2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        EditText nume = findViewById(R.id.editNume);
        EditText prenume = findViewById(R.id.editPrenume);
        EditText varsta = findViewById(R.id.editVarsta);

        RadioGroup sex = findViewById(R.id.radioSex);
        DatePicker data = findViewById(R.id.datePacient);
        Switch bolnav = findViewById(R.id.switchBolnav);
        CheckBox urgent = findViewById(R.id.checkUrgent);

        Button salveaza = findViewById(R.id.btnSalveaza);

        salveaza.setOnClickListener(v -> {

            String n = nume.getText().toString().trim();
            String p = prenume.getText().toString().trim();
            String textVarsta = varsta.getText().toString().trim();

            if (n.isEmpty() || p.isEmpty() || textVarsta.isEmpty()) {
                Toast.makeText(this,
                        "Completeaza toate campurile!",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            int ani;

            try {
                ani = Integer.parseInt(textVarsta);
            } catch (NumberFormatException e) {
                varsta.setError("Varsta invalida");
                return;
            }

            if (ani < 0 || ani > 120) {
                varsta.setError("Introdu o varsta valida");
                return;
            }

            int idSex = sex.getCheckedRadioButtonId();

            if (idSex == -1) {
                Toast.makeText(this,
                        "Selecteaza sexul!",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton selectat = findViewById(idSex);
            String sexPacient = selectat.getText().toString();

            String dataSelectata = String.format(
                    Locale.getDefault(),
                    "%02d.%02d.%04d",
                    data.getDayOfMonth(),
                    data.getMonth() + 1,
                    data.getYear()
            );

            SanatateGenerala pacient =
                    new SanatateGenerala(
                            n,
                            p,
                            ani,
                            sexPacient,
                            dataSelectata,
                            bolnav.isChecked(),
                            urgent.isChecked()
                    );

            Bundle date = new Bundle();
            date.putSerializable("pacient", pacient);

            Intent intent = new Intent();
            intent.putExtra("date", date);

            setResult(RESULT_OK, intent);
            finish();
        });
    }
}