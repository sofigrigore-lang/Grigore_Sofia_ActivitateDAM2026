package com.example.seminar2;

import android.os.Parcel;
import android.os.Parcelable;

public class SanatateGenerala implements Parcelable {

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

    private SanatateGenerala(Parcel in) {
        nume = in.readString();
        prenume = in.readString();
        varsta = in.readInt();
        sex = in.readString();
        dataCalendaristica = in.readString();
        bolnav = in.readByte() != 0;
        urgent = in.readByte() != 0;
    }

    public static final Creator<SanatateGenerala> CREATOR = new Creator<SanatateGenerala>() {
        @Override
        public SanatateGenerala createFromParcel(Parcel in) {
            return new SanatateGenerala(in);
        }

        @Override
        public SanatateGenerala[] newArray(int size) {
            return new SanatateGenerala[size];
        }
    };

    public String afisare() {
        return "Nume: " + nume +
                "\nPrenume: " + prenume +
                "\nVarsta: " + varsta +
                "\nSex: " + sex +
                "\nData: " + dataCalendaristica +
                "\nStatus: " + (bolnav ? "Bolnav" : "Sanatos") +
                "\nUrgent: " + (urgent ? "Da" : "Nu");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nume);
        dest.writeString(prenume);
        dest.writeInt(varsta);
        dest.writeString(sex);
        dest.writeString(dataCalendaristica);
        dest.writeByte((byte) (bolnav ? 1 : 0));
        dest.writeByte((byte) (urgent ? 1 : 0));
    }

    @Override
    public String toString() {
        return afisare();
    }
}
