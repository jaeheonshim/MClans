package com.jaeheonshim.simplysurvival.server.tasks;

import com.jaeheonshim.simplysurvival.server.PlayerManager;
import com.jaeheonshim.simplysurvival.server.SPlayer;
import com.jaeheonshim.simplysurvival.server.domain.PlayerProperties;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

                if(sPlayer.getTimePlayed() > 60 && !sPlayer.getProperty(PlayerProperties.PROTECTION_END_NOTIFIED).equals("true")) {
                    player.sendMessage(ChatColor.RED + "Your 60 minutes of protection from pvp and hunger are now up. Good luck!");
                    sPlayer.saveProperty(PlayerProperties.PROTECTION_END_NOTIFIED, true);
                }

                PlayerManager.getInstance().savePlayer(sPlayer);
            }

            lastTick = System.currentTimeMillis();
        }
    }
}
