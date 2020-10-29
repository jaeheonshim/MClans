package com.jaeheonshim.simplysurvival.mclans.commands;

import com.jaeheonshim.simplysurvival.mclans.ClanManager;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NewClanClanCommand extends AbstractClanCommand {
    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        ClanManager clanManager = ClanManager.getClanManager();

        Player sender1 = (Player) sender;
        if (clanManager.getClanOfPlayer(sender1.getUniqueId().toString()) != null) {
            sender.sendMessage(ChatColor.RED + "You are already in a clan! Leave that one before you create your own!");
        } else if(args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Please specify the name of your clan!");
        } else if(clanManager.getClanByName(args[1]) != null) {
            sender.sendMessage(ChatColor.RED + "A clan with that name already exists!");
        } else if(args[1].length() >= 16) {
            sender.sendMessage(ChatColor.RED + "Your clan name can not be longer than 16 characters!");
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
