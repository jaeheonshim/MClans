package com.jaeheonshim.mclans;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {
    @EventHandler
    public void handleBlockPlace(BlockPlaceEvent event) {
        Chunk chunk = event.getBlock().getChunk();
        ClanManager manager = ClanManager.getClanManager();

        Clan clanInChunk = manager.getClanInChunk(chunk);
        if(clanInChunk != null && !clanInChunk.isMember(event.getPlayer().getUniqueId().toString()) && !clanInChunk.getOwnerUuid().equals(event.getPlayer().getUniqueId().toString())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "You can't place blocks in the territory of " + clanInChunk.getName());
        }
    }
}
