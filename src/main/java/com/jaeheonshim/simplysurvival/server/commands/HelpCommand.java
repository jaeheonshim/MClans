package com.jaeheonshim.simplysurvival.server.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class HelpCommand extends AbstractServerCommand {
    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        StringBuilder helpBuilder = new StringBuilder();
        ChatColor cs = ChatColor.YELLOW;
        ChatColor ds = ChatColor.RED;

        helpBuilder.append(cs + "/clan guide" + ds + " Everything you need to know about clans\n");
        helpBuilder.append(cs + "/spawn" + ds + " Teleport to spawn\n");
        helpBuilder.append(cs + "/suicide" + ds + " Kills your character instantly\n");
        helpBuilder.append(cs + "/enablepvp" + ds + " Forefit your pvp protection (CANNOT BE UNDONE)\n");

        sender.sendMessage(helpBuilder.toString());

        return true;
    }

    @Override
    public String getKeyword() {
        return "help";
    }
}
