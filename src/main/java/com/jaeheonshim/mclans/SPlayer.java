package com.jaeheonshim.mclans;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SPlayer {
    @Id
    private String _id;
    private long timePlayed;
    private String cachedUsername;

    public SPlayer() {

    }

    public void addTimePlayed(long minutes) {
        timePlayed += minutes;
    }

    public String getCachedUsername() {
        return cachedUsername;
    }

    public void setCachedUsername(String cachedUsername) {
        this.cachedUsername = cachedUsername;
    }

    public long getTimePlayed() {
        return timePlayed;
    }

    public void setTimePlayed(long timePlayed) {
        this.timePlayed = timePlayed;
    }

    public String getUuid() {
        return _id;
    }

    public void setUuid(String uuid) {
        this._id = uuid;
    }
}
