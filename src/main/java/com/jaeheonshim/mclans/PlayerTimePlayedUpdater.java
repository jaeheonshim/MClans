package com.jaeheonshim.mclans;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerTimePlayedUpdater implements Runnable {
    private long lastTick = System.currentTimeMillis() / 1000 / 60;

    @Override
    public void run() {
        PlayerManager playerManager = PlayerManager.getInstance();
        long timePlayed = (System.currentTimeMillis() / 1000 / 60) - lastTick;

        for(Player player : Bukkit.getOnlinePlayers()) {
            SPlayer sPlayer = playerManager.getPlayer(player.getUniqueId().toString());
            if(sPlayer != null) {
                sPlayer.addTimePlayed(timePlayed);
            }
        }

        lastTick = System.currentTimeMillis() / 1000 / 60;
    }
}
