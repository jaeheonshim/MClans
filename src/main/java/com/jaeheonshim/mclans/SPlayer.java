package com.jaeheonshim.mclans;

public class SPlayer {
    private long timePlayed;
    private String cachedUsername;

    public void addTimePlayed(long minutes) {
        timePlayed += minutes;
    }

    public String getCachedUsername() {
        return cachedUsername;
    }

    public void setCachedUsername(String cachedUsername) {
        this.cachedUsername = cachedUsername;
    }
}
