package com.jaeheonshim.simplysurvival.server.listeners;

import com.jaeheonshim.simplysurvival.mclans.Clan;
import com.jaeheonshim.simplysurvival.mclans.ClanManager;
import com.jaeheonshim.simplysurvival.server.PlayerManager;
import com.jaeheonshim.simplysurvival.server.SPlayer;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.time.Duration;

public class PlayerChatListener implements Listener {
    @EventHandler
    public void handlePlayerChatEvent(AsyncPlayerChatEvent event) {
        SPlayer player = PlayerManager.getInstance().getPlayer(event.getPlayer().getUniqueId().toString());

        if(player.isMuted()) {
            Duration mutedDuration = Duration.ofMillis(player.getMutedUntil() - System.currentTimeMillis());
            String mutedMessage = ChatColor.RED + "================= YOU ARE CHAT MUTED =================\n" + ChatColor.YELLOW +
                    "You have been banned from sending messages in chat for " + ChatColor.GOLD + player.getMuteReason() + ChatColor.YELLOW + "\n" +
                    "You will be unmuted in " + ChatColor.GREEN + mutedDuration.toHours() + " hours " + mutedDuration.minusHours(mutedDuration.toHours()).toMinutes() + " minutes" + ChatColor.YELLOW + ".\n" + ChatColor.RED +
                    "====================================================";
            event.getPlayer().sendMessage(mutedMessage);
            event.setCancelled(true);
            return;
        }

        ClanManager manager = ClanManager.getClanManager();
        Clan playerClan = manager.getClanOfPlayer(event.getPlayer().getUniqueId().toString());

        String message;

        String playerFlags = player.getPrettyFlags();

        if(playerClan != null) {
            event.setFormat(playerFlags + "<[" + playerClan.getName() + "] " + ChatColor.AQUA + "%s" + ChatColor.RESET + "> %s");
        } else {
            event.setFormat(playerFlags + "<"+ ChatColor.AQUA + "%s" + ChatColor.RESET + "> %s");
        }
    }
}
