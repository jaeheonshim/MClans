package com.jaeheonshim.mclans;

import dev.morphia.Datastore;
import dev.morphia.query.Query;
import dev.morphia.query.internal.MorphiaCursor;
import org.bukkit.Bukkit;
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
        Bukkit.getLogger().info("Loading all clans from database...");
        MorphiaCursor<Clan> query = datastore.find(Clan.class).find();
        while(query.hasNext()) {
            clans.add(query.next());
        }
        Bukkit.getLogger().info("Successfully loaded all clans!");
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

    public void destroyClan(Clan clan) {
        clans.remove(clan);

        datastore.delete(clan);
    }

    public Clan getClanOfPlayer(String player) {
        for(Clan clan : clans) {
            if(clan.isMember(player)) {
                return clan;
            }
        }

        return null;
    }

    public Clan getClanByName(String name) {
        for(Clan clan : clans) {
            if(clan.getName().equalsIgnoreCase(name)) {
                return clan;
            }
        }

        return null;
    }

    public void saveClan(Clan clan) {
        Bukkit.getLogger().info("Saving clan: " + clan);
        datastore.save(clan);
    }
}
