package com.jaeheonshim.mclans.commands;

import com.jaeheonshim.mclans.ClanManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NewClanCommand extends AbstractCommand {
    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        ClanManager.getClanManager().newClan(((Player) sender).getUniqueId().toString(), args[1]);
        return true;
    }

    @Override
    public String getKeyword() {
        return "new";
    }
}
