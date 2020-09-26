package com.jaeheonshim.mclans;

import org.bukkit.Chunk;

import java.util.ArrayList;
import java.util.List;

public class ClanManager {
    private List<Clan> clans = new ArrayList<>();

    private static ClanManager clanManager = new ClanManager();

    private ClanManager() {

    }

    public static ClanManager getClanManager() {
        return clanManager;
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
}
