package com.jaeheonshim.simplysurvival.server.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SuicideCommand extends AbstractServerCommand {
    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = ((Player) sender);
            Bukkit.getServer().broadcastMessage(ChatColor.RED + player.getName() + " committed suicide.");
            player.setHealth(0);
        }

        return true;
    }

    @Override
    public String getKeyword() {
        return "suicide";
    }
}
