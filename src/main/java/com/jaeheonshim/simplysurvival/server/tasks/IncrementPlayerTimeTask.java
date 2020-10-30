package com.jaeheonshim.simplysurvival.server.tasks;

import com.jaeheonshim.simplysurvival.server.PlayerManager;
import com.jaeheonshim.simplysurvival.server.SPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class IncrementPlayerTimeTask implements Runnable {
    @Override
    public void run() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            SPlayer sPlayer = PlayerManager.getInstance().getPlayer(player.getUniqueId().toString());
            sPlayer.addTimePlayed(1);

            PlayerManager.getInstance().savePlayer(sPlayer);
        }
    }
}
