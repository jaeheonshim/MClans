package com.jaeheonshim.mclans;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(ChatColor.GREEN + "" + event.getPlayer().getName() + " has joined the server.");

        PlayerManager manager = PlayerManager.getInstance();
        if(manager.getPlayer(event.getPlayer().getUniqueId().toString()) == null) {
            manager.newPlayer(event.getPlayer().getUniqueId().toString());
        }

        manager.getPlayer(event.getPlayer().getUniqueId().toString()).setCachedUsername(event.getPlayer().getName());
    }
}
