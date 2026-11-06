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

    public static final String EXTRA_PACIENT = "pacient";
    public static final String EXTRA_EDIT = "pacient_edit";

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

        SanatateGenerala pacientInitial = getIntent().getParcelableExtra(EXTRA_EDIT);
        if (pacientInitial != null) {
            completeazaCampurile(pacientInitial, nume, prenume, varsta,
                    sex, data, bolnav, urgent);
            salveaza.setText("Salvează modificările");
        }

        salveaza.setOnClickListener(view -> {
            String textNume = nume.getText().toString().trim();
            String textPrenume = prenume.getText().toString().trim();
            String textVarsta = varsta.getText().toString().trim();

            if (textNume.isEmpty() || textPrenume.isEmpty() || textVarsta.isEmpty()) {
                Toast.makeText(this, "Completează toate câmpurile!", Toast.LENGTH_SHORT).show();
                return;
            }

            int ani;
            try {
                ani = Integer.parseInt(textVarsta);
            } catch (NumberFormatException e) {
                varsta.setError("Vârsta invalidă");
                return;
            }
            if (ani < 0 || ani > 120) {
                varsta.setError("Introdu o vârstă validă");
                return;
            }

            int idSex = sex.getCheckedRadioButtonId();
            if (idSex == -1) {
                Toast.makeText(this, "Selectează sexul!", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton selectat = findViewById(idSex);
            String textSex = selectat.getText().toString();
            String textData = String.format(Locale.getDefault(), "%02d.%02d.%04d",
                    data.getDayOfMonth(), data.getMonth() + 1, data.getYear());

            SanatateGenerala pacient = pacientInitial == null
                    ? new SanatateGenerala(textNume, textPrenume, ani, textSex,
                    textData, bolnav.isChecked(), urgent.isChecked())
                    : pacientInitial;
            pacient.nume = textNume;
            pacient.prenume = textPrenume;
            pacient.varsta = ani;
            pacient.sex = textSex;
            pacient.dataCalendaristica = textData;
            pacient.bolnav = bolnav.isChecked();
            pacient.urgent = urgent.isChecked();

            Intent rezultat = new Intent();
            rezultat.putExtra(EXTRA_PACIENT, pacient);
            setResult(RESULT_OK, rezultat);
            finish();
        });
    }

    private void completeazaCampurile(SanatateGenerala pacient, EditText nume,
                                      EditText prenume, EditText varsta,
                                      RadioGroup sex, DatePicker data,
                                      Switch bolnav, CheckBox urgent) {
        nume.setText(pacient.nume);
        prenume.setText(pacient.prenume);
        varsta.setText(String.valueOf(pacient.varsta));
        bolnav.setChecked(pacient.bolnav);
        urgent.setChecked(pacient.urgent);

        for (int index = 0; index < sex.getChildCount(); index++) {
            RadioButton radioButton = (RadioButton) sex.getChildAt(index);
            if (radioButton.getText().toString().equals(pacient.sex)) {
                sex.check(radioButton.getId());
                break;
            }
        }

        String[] partiData = pacient.dataCalendaristica.split("\\.");
        if (partiData.length == 3) {
            try {
                data.updateDate(Integer.parseInt(partiData[2]),
                        Integer.parseInt(partiData[1]) - 1,
                        Integer.parseInt(partiData[0]));
            } catch (NumberFormatException ignored) {
            }
        }
    }
}
