package com.jaeheonshim.mclans;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Transient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class SPlayer {
    @Id
    private String _id;
    private long timePlayed;
    private String cachedUsername;

    @Transient
    private long destroyClanTimeStamp;

    @Transient
    private Set<ClanInvitation> invitations = new HashSet<>();

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

    public void addInvitation(ClanInvitation invitation) {
        invitations.add(invitation);
    }

    public boolean isInvited(Clan clan) {
        for(ClanInvitation invitation : invitations) {
            if(invitation.getClanId().equalsIgnoreCase(clan.getId().toString())) {
                return true;
            }
        }
        return false;
    }

    public ClanInvitation getInvitation(Clan clan) {
        for(ClanInvitation invitation : invitations) {
            if(invitation.getClanId().equalsIgnoreCase(clan.getId().toString())) {
                return invitation;
            }
        }
        return null;
    }

    public boolean isConfirmDestroy() {
        return destroyClanTimeStamp + Constant.CLAN_DESTROY_CONFIRM_TIMEOUT >= System.currentTimeMillis();
    }

    public void queueDestroy() {
        destroyClanTimeStamp = System.currentTimeMillis();
    }
}
