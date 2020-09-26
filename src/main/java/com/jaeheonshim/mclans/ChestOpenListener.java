package com.jaeheonshim.mclans;

import org.bukkit.ChatColor;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class ChestOpenListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void handleInventoryOpen(InventoryOpenEvent event) {
        if(event.getInventory().getHolder() instanceof Chest || event.getInventory().getHolder() instanceof DoubleChest) {
            ClanManager manager = ClanManager.getClanManager();
            if(manager.getClanInChunk(event.getInventory().getLocation().getChunk()) != null && !manager.getClanInChunk(event.getInventory().getLocation().getChunk()).isMember(event.getPlayer().getUniqueId().toString())) {
                event.getPlayer().sendMessage(ChatColor.RED + "You're not allowed to open that.");
                event.setCancelled(true);
            }
        }
    }
}
