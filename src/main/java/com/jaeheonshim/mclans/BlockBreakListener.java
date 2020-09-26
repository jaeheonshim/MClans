package com.jaeheonshim.mclans;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void handleBlockBreak(BlockBreakEvent event) {
        Chunk chunk = event.getBlock().getChunk();
        ClanManager manager = ClanManager.getClanManager();

        Clan clanInChunk = manager.getClanInChunk(chunk);
        if(clanInChunk != null && !clanInChunk.isMember(event.getPlayer().getUniqueId().toString()) && !clanInChunk.getOwnerUuid().equals(event.getPlayer().getUniqueId().toString())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "You can't break blocks in the territory of " + clanInChunk.getName());
        }
    }
}
