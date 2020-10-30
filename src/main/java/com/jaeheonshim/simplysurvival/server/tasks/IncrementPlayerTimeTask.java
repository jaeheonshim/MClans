package com.jaeheonshim.simplysurvival.server.tasks;

import com.jaeheonshim.simplysurvival.server.PlayerManager;
import com.jaeheonshim.simplysurvival.server.SPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.Duration;

public class IncrementPlayerTimeTask implements Runnable {
    private long lastTick = System.currentTimeMillis();

    @Override
    public void run() {
        long incrementMinutes = Duration.ofMillis(System.currentTimeMillis() - lastTick).toMinutes();

        if(incrementMinutes >= 1) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                SPlayer sPlayer = PlayerManager.getInstance().getPlayer(player.getUniqueId().toString());
                sPlayer.addTimePlayed(incrementMinutes);

                PlayerManager.getInstance().savePlayer(sPlayer);
            }

            lastTick = System.currentTimeMillis();
        }
    }
}
