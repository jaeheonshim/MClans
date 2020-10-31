package com.jaeheonshim.simplysurvival.server.listeners;

import com.jaeheonshim.simplysurvival.server.PlayerManager;
import com.jaeheonshim.simplysurvival.server.SPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerTakeDamageListener implements Listener {
    @EventHandler
    public void handlePlayerDamage(EntityDamageEvent event) {
        if(event.getEntityType() == EntityType.PLAYER) {
            SPlayer player = PlayerManager.getInstance().getPlayer(event.getEntity().getUniqueId().toString());
            player.recordDamage();
        }
    }
}
