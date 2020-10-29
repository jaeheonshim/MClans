package com.jaeheonshim.simplysurvival.server.listeners;

import com.jaeheonshim.simplysurvival.mclans.Clan;
import com.jaeheonshim.simplysurvival.mclans.ClanManager;
import com.jaeheonshim.simplysurvival.server.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {
    @EventHandler
    public void handlePlayerChatEvent(AsyncPlayerChatEvent event) {
        ClanManager manager = ClanManager.getClanManager();
        Clan playerClan = manager.getClanOfPlayer(event.getPlayer().getUniqueId().toString());

        String message;

        String playerFlags = PlayerManager.getInstance().getPlayer(event.getPlayer().getUniqueId().toString()).getPrettyFlags();

        if(playerClan != null) {
            event.setFormat(playerFlags + "<[" + playerClan.getName() + "] " + ChatColor.AQUA + "%s" + ChatColor.RESET + "> %s");
        } else {
            event.setFormat(playerFlags + "<"+ ChatColor.AQUA + "%s" + ChatColor.RESET + "> %s");
        }
    }
}
