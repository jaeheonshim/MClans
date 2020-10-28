package com.jaeheonshim.mclans.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class AbstractCommand {
    public abstract boolean execute(CommandSender sender, Command command, String label, String[] args);
    public abstract String getKeyword();
}
