package com.jaeheonshim.mclans;

public class SPlayer {
    private boolean isNew;
    private int balance;
    private long timePlayed;

    public void addTimePlayed(long minutes) {
        timePlayed += minutes;
    }
}
