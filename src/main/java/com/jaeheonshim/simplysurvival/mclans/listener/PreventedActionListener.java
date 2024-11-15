package com.jaeheonshim.simplysurvival.mclans.listener;

import com.jaeheonshim.simplysurvival.mclans.Clan;
import com.jaeheonshim.simplysurvival.mclans.ClanManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PreventedActionListener implements Listener {
    @EventHandler
    public void handleBucketEmpty(PlayerBucketEmptyEvent event) {
        handlePreventedAction(event.getBlockClicked().getChunk(), event, event.getPlayer());
    }

    @EventHandler
    public void handleBucketFill(PlayerBucketFillEvent event) {
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
        if(clanInChunk != null && !clanInChunk.isMember(player.getUniqueId().toString()) && !clanInChunk.getOwnerUuid().equals(player.getUniqueId().toString())  && player.getGameMode() != GameMode.CREATIVE) {
            cancellable.setCancelled(true);
            player.sendMessage(ChatColor.RED + "You can't use that in the territory of " + clanInChunk.getName());
        }
    }
}
