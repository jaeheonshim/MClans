package com.jaeheonshim.simplysurvival.server.listeners;

import com.jaeheonshim.simplysurvival.server.PlayerManager;
import com.jaeheonshim.simplysurvival.server.SPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class HungerTickListener implements Listener {
    @EventHandler
    public void handleHungerEvent(FoodLevelChangeEvent event) {
        if(event.getEntity() instanceof Player) {
            SPlayer player = PlayerManager.getInstance().getPlayer(event.getEntity().getUniqueId().toString());
            if(player.getTimePlayed() < 60) {
                event.setCancelled(true);
                ((Player) event.getEntity()).setFoodLevel(20);
            }
        }
    }
}
