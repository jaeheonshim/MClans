package com.jaeheonshim.simplysurvival.mclans.listener;

import com.jaeheonshim.simplysurvival.mclans.Clan;
import com.jaeheonshim.simplysurvival.mclans.ClanManager;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {
    @EventHandler
    private void onPlayerMove(PlayerMoveEvent event) {
        if(!event.getFrom().getChunk().equals(event.getTo().getChunk())) {
            ClanManager manager = ClanManager.getClanManager();

            Chunk prevChunk = event.getFrom().getChunk();
            Chunk toChunk = event.getTo().getChunk();

            Clan prevClan = manager.getClanInChunk(prevChunk);
            Clan toClan = manager.getClanInChunk(toChunk);

            if(prevClan != null && prevClan.equals(toClan)) {
                return;
            } else if(toClan != null) {
                event.getPlayer().sendMessage(ChatColor.RED + "You are now entering the territory of " + toClan.getName());
            } else if(prevClan != null) {
                event.getPlayer().sendMessage(ChatColor.GREEN + "Entering unclaimed territory");
            }
        }
    }
}
