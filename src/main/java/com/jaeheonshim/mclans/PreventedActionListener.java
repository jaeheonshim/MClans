package com.jaeheonshim.mclans;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PreventedActionListener implements Listener {
    @EventHandler
    public void handleBucketEmpty(PlayerBucketEmptyEvent event) {
        handlePreventedAction(event.getBlockClicked().getChunk(), event, event.getPlayer());
    }

    @EventHandler
    public void handleBoneMeal(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.hasItem() && event.getItem().getType() == Material.INK_SACK) {
            Bukkit.getLogger().info(event.getClickedBlock().getChunk().toString());
            handlePreventedAction(event.getClickedBlock().getChunk(), event, event.getPlayer());
        }
    }

    public void handlePreventedAction(Chunk chunk, Cancellable cancellable, Player player) {
        ClanManager manager = ClanManager.getClanManager();

        Clan clanInChunk = manager.getClanInChunk(chunk);
        if(clanInChunk != null && !clanInChunk.isMember(player.getUniqueId().toString()) && !clanInChunk.getOwnerUuid().equals(player.getUniqueId().toString())) {
            cancellable.setCancelled(true);
            player.sendMessage(ChatColor.RED + "You can't use that in the territory of " + clanInChunk.getName());
        }
    }
}
