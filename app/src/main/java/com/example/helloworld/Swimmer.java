package com.example.helloworld;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;

public class Swimmer implements Parcelable {

    public static final Creator<Swimmer> CREATOR = new Creator<Swimmer>() {
        @Override
        public Swimmer createFromParcel(Parcel in) {
            return new Swimmer(in);
        }

        @Override
        public Swimmer[] newArray(int size) {
            return new Swimmer[size];
        }
    };

    private String name;
    private ArrayList<Lap> laps;

    public Swimmer() {
        laps = new ArrayList<>();
    }
    public Swimmer (String name) {
        this.name = name;
        laps = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName () {
        return name;
    }

    public void addLaps(long overallTime) {
        laps.add(new Lap(overallTime, laps.size()));
        if (laps.size() == 1) {
            laps.get(laps.size()-1).setSplitTime(overallTime);
        }
        else {
            laps.get(laps.size()-1).setSplitTime(overallTime - laps.get(laps.size()-2).getOverallTime());
        }

    }

    public ArrayList<Lap> getLaps () {
        laps.add(0, null);
        return laps;
    }

    public Swimmer(Parcel in) {
        this.name = in.readString();
        this.laps = in.readArrayList(Lap.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) { // TODO Parcel: unable to marshal value com.example.helloworld.Swimmer$Lap@35861eb
        dest.writeString(name);
        dest.writeList(laps);
    }

    public class Lap implements Parcelable {

        private int lapNumber;
        private long overallTime;
        private long splitTime;

        public Lap(long overallTime, int lapNumber) {
            this.overallTime = overallTime;
            this.lapNumber = lapNumber;
        }

        protected Lap(Parcel in) {
            lapNumber = in.readInt();
            overallTime = in.readLong();
            splitTime = in.readLong();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(lapNumber);
            dest.writeLong(overallTime);
            dest.writeLong(splitTime);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Lap> CREATOR = new Creator<Lap>() {
            @Override
            public Lap createFromParcel(Parcel in) {
                return new Lap(in);
            }

            @Override
            public Lap[] newArray(int size) {
                return new Lap[size];
            }
        };

        public void setSplitTime (long splitTime) {
            this.splitTime = splitTime;
        }
        public long getOverallTime () {
            return overallTime;
        }
        public String getOverallClockTime () {
            return changeFormat(overallTime);
        }
        public String getSplitClockTime () {
            return changeFormat(splitTime);
        }
        public String changeFormat(long newCurrentCentiseconds) {
            int seconds = (int) (newCurrentCentiseconds / 100);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            int centiseconds =  (int) (newCurrentCentiseconds % 100);

            return String.format("%d:%02d.%02d", minutes,seconds,centiseconds);
        }

    }
}
