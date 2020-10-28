package com.jaeheonshim.mclans.commands;

import com.jaeheonshim.mclans.ClanManager;
import dev.morphia.Datastore;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClaimCommand extends AbstractCommand {
    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        ClanManager manager = ClanManager.getClanManager();
        Player player = ((Player) sender);
        if(manager.getClanOfPlayer(player.getUniqueId().toString()) == null) {
            player.sendMessage(ChatColor.RED + "You aren't in a clan!");
            return true;
        }

        if(player.getLocation().getWorld().getEnvironment() == World.Environment.THE_END) {
            player.sendMessage(ChatColor.RED + "You can't claim land here.");
            return true;
        }

        if(manager.getClanOfPlayer(player.getUniqueId().toString()).claim(player.getLocation().getChunk())) {
            player.sendMessage(ChatColor.GREEN + "Chunk successfully claimed!");
            manager.saveClan(manager.getClanOfPlayer(player.getUniqueId().toString()));
        } else {
            player.sendMessage(ChatColor.RED + "This chunk is already claimed!");
        }

        return true;
    }

    @Override
    public String getKeyword() {
        return "claim";
    }
}
