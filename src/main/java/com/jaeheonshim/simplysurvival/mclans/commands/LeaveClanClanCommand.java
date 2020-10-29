package com.jaeheonshim.simplysurvival.mclans.commands;

import com.jaeheonshim.simplysurvival.mclans.Clan;
import com.jaeheonshim.simplysurvival.mclans.ClanManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LeaveClanClanCommand extends AbstractClanCommand {
    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        ClanManager manager = ClanManager.getClanManager();
        Player player = ((Player) sender);
        if(manager.getClanOfPlayer(player.getUniqueId().toString()) == null) {
            player.sendMessage(ChatColor.RED + "You aren't in a clan!");
            return true;
        }

        Clan clan = manager.getClanOfPlayer(player.getUniqueId().toString());
        if(clan.getOwnerUuid().equalsIgnoreCase(player.getUniqueId().toString())) {
            player.sendMessage(ChatColor.RED + "The owner can't abandon their clan!");
            return true;
        }

        clan.removeMember(player.getUniqueId().toString());
        manager.saveClan(clan);

        clan.broadcastToOnline(ChatColor.YELLOW + player.getName() + ChatColor.GREEN + " has left the clan.");
        player.sendMessage(ChatColor.GREEN + "You have left the clan " + ChatColor.YELLOW + clan.getName());

        return true;
    }

    @Override
    public String getKeyword() {
        return "leave";
    }
}
