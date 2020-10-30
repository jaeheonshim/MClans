package com.jaeheonshim.simplysurvival.mclans.commands;

import com.jaeheonshim.simplysurvival.mclans.Clan;
import com.jaeheonshim.simplysurvival.mclans.ClanManager;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClaimClanCommand extends AbstractClanCommand {
    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        ClanManager manager = ClanManager.getClanManager();
        Player player = ((Player) sender);
        Clan clanOfPlayer = manager.getClanOfPlayer(player.getUniqueId().toString());
        if(clanOfPlayer == null) {
            player.sendMessage(ChatColor.RED + "You aren't in a clan!");
            return true;
        }

        if(player.getLocation().getWorld().getEnvironment() == World.Environment.THE_END) {
            player.sendMessage(ChatColor.RED + "You can't claim land here.");
            return true;
        }

        if(clanOfPlayer.getClaimAmount() < clanOfPlayer.getClaimableAmount()) {
            if (clanOfPlayer.claim(player.getLocation().getChunk())) {
                player.sendMessage(ChatColor.GREEN + "Chunk successfully claimed!");
                manager.saveClan(clanOfPlayer);
            } else {
                player.sendMessage(ChatColor.RED + "This chunk is already claimed!");
            }
        } else {
            player.sendMessage(ChatColor.RED + "Your clan doesn't have any land claims remaining!");
        }

        return true;
    }

    @Override
    public String getKeyword() {
        return "claim";
    }
}
