package com.jaeheonshim.simplysurvival.mclans.listener;

import com.jaeheonshim.simplysurvival.server.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void handlePlayerJoin(PlayerJoinEvent event) {
        PlayerManager manager = PlayerManager.getInstance();
        String cachedUsername = manager.getPlayer(event.getPlayer().getUniqueId().toString()).getCachedUsername();
        if (!cachedUsername.equalsIgnoreCase(event.getPlayer().getName())) {
            Bukkit.getLogger().info("Player " + event.getPlayer().getUniqueId().toString() + " changed their username from " + cachedUsername + " to " + event.getPlayer().getName() + "! Updating in database");
            manager.getPlayer(event.getPlayer().getUniqueId().toString()).setCachedUsername(cachedUsername);
            PlayerManager.getInstance().savePlayer(manager.getPlayer(event.getPlayer().getUniqueId().toString()));
        }
    }
}
