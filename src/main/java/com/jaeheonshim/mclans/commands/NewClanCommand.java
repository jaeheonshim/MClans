package com.jaeheonshim.mclans.commands;

import com.jaeheonshim.mclans.ClanManager;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NewClanCommand extends AbstractCommand {
    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        ClanManager clanManager = ClanManager.getClanManager();

        Player sender1 = (Player) sender;
        if (clanManager.getClanOfPlayer(sender1.getUniqueId().toString()) != null) {
            sender.sendMessage(ChatColor.RED + "You are already in a clan! Leave that one before you create your own!");
        } else {
            clanManager.newClan(sender1.getUniqueId().toString(), args[1]);
            sender.sendMessage(ChatColor.GREEN + "You have created a new clan!");
            sender1.playSound(sender1.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 10, 1);
        }
        return true;
    }

    @Override
    public String getKeyword() {
        return "new";
    }
}
