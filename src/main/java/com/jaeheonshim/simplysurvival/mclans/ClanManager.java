package com.jaeheonshim.simplysurvival.mclans;

import com.jaeheonshim.simplysurvival.server.PlayerManager;
import com.jaeheonshim.simplysurvival.server.SPlayer;
import dev.morphia.Datastore;
import dev.morphia.query.internal.MorphiaCursor;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;

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
        Clan clan = clanManager.newSystemClan("Spawn");

        for(World world : Bukkit.getWorlds()) {
            if(world.getEnvironment() == World.Environment.NORMAL) {
                int spawnX = world.getSpawnLocation().getChunk().getX();
                int spawnZ = world.getSpawnLocation().getChunk().getZ();
                int spawnRadius = 5;

                for(int i = spawnX - 5; i <= spawnX + 5; i++) {
                    for(int j = spawnZ - 5; j <= spawnZ + 5; j++) {
                        clan.claim(world.getChunkAt(i, j));
                    }
                }
            }
        }
    }

    public static ClanManager getClanManager() {
        return clanManager;
    }

    public void loadClans() throws RuntimeException {
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

    public Clan newSystemClan(String name) {
        Clan newClan = new Clan(name, "");
        clans.add(newClan);
        newClan.setSystemClan(true);

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

    public void handleVote(String username) {
        Bukkit.getLogger().info(username + " voted!");

        String uuidCached = PlayerManager.getInstance().getCachedUuid(username);

        if(uuidCached != null) {
            Clan clan = getClanOfPlayer(uuidCached);

            if(clan != null) {
                Bukkit.getLogger().info("Registering vote by " + username + " for clan " + clan.getName());

                clan.addVote();
                if(clan.getClanVotes() >= Constant.CLAN_VOTE_LAND_AMOUNT) {
                    clan.incrementClaimableAmount();
                    clan.setClanVotes(0);
                }

                saveClan(clan);
            }
        }
    }
}
