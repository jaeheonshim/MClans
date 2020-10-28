package com.jaeheonshim.mclans;

import dev.morphia.Datastore;
import dev.morphia.query.Query;
import dev.morphia.query.internal.MorphiaCursor;
import org.bukkit.Chunk;

import java.util.ArrayList;
import java.util.List;

public class ClanManager {
    private final Datastore datastore;
    private List<Clan> clans = new ArrayList<>();

    private static ClanManager clanManager;

    private ClanManager(Datastore datastore) {
        this.datastore = datastore;
    }

    public static void init(Datastore datastore) {
        clanManager = new ClanManager(datastore);
    }

    public static ClanManager getClanManager() {
        return clanManager;
    }

    public void loadClans() {
        MorphiaCursor<Clan> query = datastore.find(Clan.class).find();
        System.out.println(datastore.find(Clan.class).count());
        while(query.hasNext()) {
            clans.add(query.next());
        }
    }

    public Clan getClanInChunk(Chunk chunk) {
        for(Clan clan : clans) {
            if(clan.ownsChunk(chunk)) {
                return clan;
            }
        }

        return null;
    }

    public Clan newClan(String player, String name) {
        Clan newClan = new Clan(name, player);
        clans.add(newClan);

        saveClan(newClan);

        return newClan;
    }

    public Clan getClanOfPlayer(String player) {
        for(Clan clan : clans) {
            if(clan.isMember(player)) {
                return clan;
            }
        }

        return null;
    }

    public void saveClan(Clan clan) {
        datastore.save(clan);
    }
}
