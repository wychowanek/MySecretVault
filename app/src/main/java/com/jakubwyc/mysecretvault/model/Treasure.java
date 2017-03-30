package com.jakubwyc.mysecretvault.model;


import android.os.Parcel;
import android.os.Parcelable;

public class Treasure implements Parcelable {

    private String text;

    private long date;

    public Treasure(final String text, final long date) {
        this.text = text;
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public long getDate() {
        return date;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.text);
        dest.writeLong(this.date);
    }

    protected Treasure(Parcel in) {
        this.text = in.readString();
        this.date = in.readLong();
    }

    public static final Creator<Treasure> CREATOR = new Creator<Treasure>() {
        @Override
        public Treasure createFromParcel(Parcel source) {
            return new Treasure(source);
        }

        @Override
        public Treasure[] newArray(int size) {
            return new Treasure[size];
        }
    };
}
