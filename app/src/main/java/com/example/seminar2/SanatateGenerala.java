package com.example.seminar2;

import java.io.Serializable;

public class SanatateGenerala implements Serializable {

    String nume;
    String prenume;
    int varsta;
    String sex;
    String dataCalendaristica;
    boolean bolnav;
    boolean urgent;

    public SanatateGenerala(String nume, String prenume,
                            int varsta, String sex,
                            String dataCalendaristica,
                            boolean bolnav, boolean urgent) {

        this.nume = nume;
        this.prenume = prenume;
        this.varsta = varsta;
        this.sex = sex;
        this.dataCalendaristica = dataCalendaristica;
        this.bolnav = bolnav;
        this.urgent = urgent;
    }

    public String afisare() {
        return "Nume: " + nume +
                "\nPrenume: " + prenume +
                "\nVarsta: " + varsta +
                "\nSex: " + sex +
                "\nData: " + dataCalendaristica +
                "\nStatus: " + (bolnav ? "Bolnav" : "Sanatos") +
                "\nUrgent: " + (urgent ? "Da" : "Nu");
    }
}