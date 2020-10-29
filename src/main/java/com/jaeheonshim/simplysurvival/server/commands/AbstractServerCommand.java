package com.jaeheonshim.simplysurvival.server.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class AbstractServerCommand {
    public abstract boolean execute(CommandSender sender, Command command, String label, String[] args);
    public abstract String getKeyword();
}
