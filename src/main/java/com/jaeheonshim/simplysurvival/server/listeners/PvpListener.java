package com.jaeheonshim.simplysurvival.server.listeners;

import com.jaeheonshim.simplysurvival.mclans.ClanManager;
import com.jaeheonshim.simplysurvival.server.PlayerManager;
import com.jaeheonshim.simplysurvival.server.SPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PvpListener implements Listener {
    @EventHandler
    public void handlePvp(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            SPlayer player = PlayerManager.getInstance().getPlayer(((Player) event.getEntity()));
            SPlayer damager = PlayerManager.getInstance().getPlayer(((Player) event.getDamager()));

            if(player.getTimePlayed() < 60 && !player.isEnabledPvp()) {
                event.setCancelled(true);

                event.getDamager().sendMessage(ChatColor.RED + "That player is protected from PVP!");
            } else if(damager.getTimePlayed() < 60 && !damager.isEnabledPvp()) {
                event.setCancelled(true);

                event.getDamager().sendMessage(ChatColor.RED + "You are protected from pvp and cannot damage other players! You can forfeit this protection with " + ChatColor.YELLOW + "/enablepvp" + ChatColor.RED + ".");
            } else if(ClanManager.getClanManager().getClanOfPlayer(player.getUuid()).equals(ClanManager.getClanManager().getClanOfPlayer(damager.getUuid()))) {
                event.setCancelled(true);

                event.getDamager().sendMessage(ChatColor.RED + "You can't attack your clanmates!");
            }
        }
    }
}
