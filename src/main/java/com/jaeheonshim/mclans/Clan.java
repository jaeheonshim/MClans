package com.jaeheonshim.mclans;

import dev.morphia.annotations.Converters;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import org.bson.types.ObjectId;
import org.bukkit.Chunk;
import java.util.HashSet;
import java.util.Set;

@Entity("Clans")
public class Clan {
    @Id
    private ObjectId id;
    private String name;
    private Set<DataChunk> landClaims = new HashSet<>();
    private String ownerUuid;
    private Set<String> members = new HashSet<>();

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

    public Set<String> getMembers() {
        return new HashSet<>(members);
    }

    public int getClaimAmount() {
        return landClaims.size();
    }

    @Override
    public String toString() {
        return "Clan{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
