package com.jaeheonshim.simplysurvival.mclans.listener;

import com.jaeheonshim.simplysurvival.mclans.Clan;
import com.jaeheonshim.simplysurvival.mclans.ClanManager;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.Iterator;

public class ExplosionListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void handleExplosion(EntityExplodeEvent event) {
        Iterator<Block> iterator = event.blockList().iterator();
        while(iterator.hasNext()) {
            Block block = iterator.next();
            ClanManager manager = ClanManager.getClanManager();

            Clan clanInChunk = manager.getClanInChunk(block.getChunk());
            if (clanInChunk != null && (clanInChunk.isSystemClan() || block.getType() == Material.CHEST)) {
                iterator.remove();
            }
        }
    }
}
