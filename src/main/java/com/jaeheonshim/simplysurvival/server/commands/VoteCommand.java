package com.jaeheonshim.simplysurvival.server.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class VoteCommand extends AbstractServerCommand {
    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(ChatColor.BLUE + "You can vote for SimplySurvival here: " + ChatColor.AQUA + ChatColor.UNDERLINE + "https://minecraftservers.org/server/597875" + ChatColor.RESET + ChatColor.BLUE + ". Thanks for voting!");
        return true;
    }

    @Override
    public String getKeyword() {
        return "vote";
    }
}
