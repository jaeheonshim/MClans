package com.jaeheonshim.simplysurvival.mclans.commands;

import com.jaeheonshim.simplysurvival.mclans.ClanManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnclaimCommand extends AbstractCommand {
    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        ClanManager manager = ClanManager.getClanManager();
        Player player = ((Player) sender);
        if(manager.getClanOfPlayer(player.getUniqueId().toString()) == null) {
            player.sendMessage(ChatColor.RED + "You aren't in a clan!");
            return true;
        }

        if(args.length == 3) {
            if(manager.getClanOfPlayer(player.getUniqueId().toString()).unclaim(player.getWorld().getChunkAt(Integer.parseInt(args[1]), Integer.parseInt(args[2])))) {
                player.sendMessage(ChatColor.GREEN + "Chunk successfully unclaimed!");
                manager.saveClan(manager.getClanOfPlayer(player.getUniqueId().toString()));
            } else {
                player.sendMessage(ChatColor.RED + "You don't have a claim on that chunk!");
            }
        } else {
            if (manager.getClanOfPlayer(player.getUniqueId().toString()).unclaim(player.getLocation().getChunk())) {
                player.sendMessage(ChatColor.GREEN + "Chunk successfully unclaimed!");
                manager.saveClan(manager.getClanOfPlayer(player.getUniqueId().toString()));
            } else {
                player.sendMessage(ChatColor.RED + "You don't have a claim on that chunk!");
            }
        }

        return true;
    }

    @Override
    public String getKeyword() {
        return "unclaim";
    }
}
