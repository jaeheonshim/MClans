package com.jaeheonshim.mclans;

import java.time.Duration;
import java.util.Objects;

public class ClanInvitation {
    public static final long EXPIRATION_DURATION = Duration.ofMinutes(5).toMillis();

    private String clanId;
    private long sentTime;
    private String sentUser;

    public ClanInvitation(String clanId, long sentTime, String sentUser) {
        this.clanId = clanId;
        this.sentTime = sentTime;
        this.sentUser = sentUser;
    }

    public String getClanId() {
        return clanId;
    }

    public long getSentTime() {
        return sentTime;
    }

    public String getSentUser() {
        return sentUser;
    }

    public boolean isExpired() {
        return sentTime + EXPIRATION_DURATION <= System.currentTimeMillis();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClanInvitation that = (ClanInvitation) o;
        return Objects.equals(clanId, that.clanId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clanId);
    }
}
