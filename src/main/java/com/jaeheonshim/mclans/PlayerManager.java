package com.jaeheonshim.mclans;

import dev.morphia.Datastore;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManager {
    private Map<String, SPlayer> playerMap = new HashMap<>();

    private static PlayerManager instance;
    private Datastore datastore;

    private PlayerManager(Datastore datastore) {
        this.datastore = datastore;
    }

    public static void init(Datastore datastore) {
        instance = new PlayerManager(datastore);
    }

    public SPlayer getPlayer(String uuid) {
        SPlayer player = datastore.find(SPlayer.class).field("_id").equal(uuid).first();

        if(player != null) {
            playerMap.put(uuid, player);
        } else if(playerMap.get(uuid) == null) {
            SPlayer newPlayer = newPlayer(uuid, Bukkit.getPlayer(UUID.fromString(uuid)).getName());
            playerMap.put(uuid, newPlayer);
            datastore.save(newPlayer);
        }

        return playerMap.get(uuid);
    }

    public static PlayerManager getInstance() {
        return instance;
    }

    public SPlayer newPlayer(String uuid, String cachedUsername) {
        SPlayer sPlayer = new SPlayer();
        sPlayer.setUuid(uuid);
        sPlayer.setCachedUsername(cachedUsername);
        playerMap.put(uuid, sPlayer);

        return sPlayer;
    }

    public void savePlayer(SPlayer player) {
        datastore.save(player);
    }
}
