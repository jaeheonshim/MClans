package com.jaeheonshim.simplysurvival.mclans;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Transient;
import org.bson.types.ObjectId;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

@Entity("Clans")
public class Clan {
    @Id
    private ObjectId id;
    private String name;
    private int claimableAmount = 5;
    private Set<DataChunk> landClaims = new HashSet<>();
    private String ownerUuid;
    private Set<String> members = new HashSet<>();
    private long onlineTimer;

    private int defensePts = 10;

    private int clanVotes;

    @Transient
    private boolean systemClan = false;

    public Clan() {

    }

    public Clan(String name, String ownerUuid) {
        this.name = name;
        this.ownerUuid = ownerUuid;
    }

    public boolean ownsChunk(Chunk chunk) {
        return landClaims.contains(new DataChunk(chunk));
    }

    public String getName() {
        return name;
    }

    public boolean claim(Chunk chunk) {
        if(ClanManager.getClanManager().getClanInChunk(chunk) == null) {
            landClaims.add(new DataChunk(chunk));
            return true;
        } else {
            return false;
        }
    }

    public boolean unclaim(Chunk chunk) {
        if(ownsChunk(chunk)) {
            landClaims.remove(new DataChunk(chunk));
            return true;
        } else {
            return false;
        }
    }

    public boolean addMember(String uuid) {
        members.add(uuid);

        return true;
    }

    public boolean removeMember(String uuid) {
        return members.remove(uuid);
    }

    public boolean isMember(String uuid) {
        return uuid.equals(ownerUuid) || members.contains(uuid);
    }

    public String getOwnerUuid() {
        return ownerUuid;
    }

    public Set<String> getMembers() {
        return new HashSet<>(members);
    }

    public int getClaimAmount() {
        return landClaims.size();
    }

    public Set<DataChunk> getLandClaims() {
        return new HashSet<>(landClaims);
    }

    public ObjectId getId() {
        return id;
    }

    public void broadcastToOnline(String message) {
        for(Player player : Bukkit.getOnlinePlayers()) {
            if(members.contains(player.getUniqueId().toString()) || ownerUuid.equalsIgnoreCase(player.getUniqueId().toString())) {
                player.sendMessage(message);
            }
        }
    }

    public int getClaimableAmount() {
        return claimableAmount;
    }

    public boolean isSystemClan() {
        return systemClan;
    }

    public void setSystemClan(boolean systemClan) {
        this.systemClan = systemClan;
    }

    @Override
    public String toString() {
        return "Clan{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public long getOnlineTimer() {
        return onlineTimer;
    }

    public long incrementOnlineTimer(long l) {
        onlineTimer += l;
        return onlineTimer;
    }

    public void resetOnlineTimer() {
        onlineTimer = 0;
    }

    public void incrementClaimableAmount() {
        claimableAmount += 1;
    }

    public void decrementDefensePoints(int decrVal) {
        if(defensePts - decrVal < 0) {
            decrVal = 0;
        } else {
            defensePts -= decrVal;
        }
    }

    public int getClanVotes() {
        return clanVotes;
    }

    public void addVote() {
        clanVotes++;
    }

    public void setClanVotes(int clanVotes) {
        this.clanVotes = clanVotes;
    }
}
