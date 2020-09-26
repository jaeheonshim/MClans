package com.jaeheonshim.mclans;

import org.bukkit.Chunk;

import java.util.HashSet;
import java.util.Set;

public class Clan {
    private String name;
    private Set<Chunk> landClaims = new HashSet<>();
    private String ownerUuid;
    private Set<String> members = new HashSet<>();

    public Clan(String name, String ownerUuid) {
        this.name = name;
        this.ownerUuid = ownerUuid;
    }

    public boolean ownsChunk(Chunk chunk) {
        return landClaims.contains(chunk);
    }

    public String getName() {
        return name;
    }

    public boolean claim(Chunk chunk) {
        if(ClanManager.getClanManager().getClanInChunk(chunk) == null) {
            landClaims.add(chunk);
            return true;
        } else {
            return false;
        }
    }

    public boolean addMember(String uuid) {
        members.add(uuid);

        return true;
    }

    public boolean isMember(String uuid) {
        return uuid.equals(ownerUuid) || members.contains(uuid);
    }

    public String getOwnerUuid() {
        return ownerUuid;
    }
}
