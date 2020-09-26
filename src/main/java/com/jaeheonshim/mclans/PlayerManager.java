package com.jaeheonshim.mclans;

import java.util.HashMap;
import java.util.Map;

public class PlayerManager {
    private Map<String, SPlayer> playerMap = new HashMap<>();

    private static PlayerManager instance = new PlayerManager();

    private PlayerManager() {

    }

    public SPlayer getPlayer(String uuid) {
        return playerMap.get(uuid);
    }

    public static PlayerManager getInstance() {
        return instance;
    }
}
