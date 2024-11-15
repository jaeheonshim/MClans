package com.jaeheonshim.simplysurvival.mclans.commands;

import com.jaeheonshim.simplysurvival.mclans.Clan;
import com.jaeheonshim.simplysurvival.mclans.ClanManager;
import com.jaeheonshim.simplysurvival.mclans.Constant;
import com.jaeheonshim.simplysurvival.server.PlayerManager;
import com.jaeheonshim.simplysurvival.server.SPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.StringJoiner;

public class InfoClanCommand extends AbstractClanCommand {
    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        ClanManager manager = ClanManager.getClanManager();
        PlayerManager playerManager = PlayerManager.getInstance();
        Player player = ((Player) sender);

        if(manager.getClanOfPlayer(player.getUniqueId().toString()) == null) {
            player.sendMessage(ChatColor.RED + "You aren't in a clan!");
            return true;
        }

        Clan clan = manager.getClanOfPlayer(player.getUniqueId().toString());
        SPlayer owner = playerManager.getPlayer(clan.getOwnerUuid());

        StringBuilder message = new StringBuilder();
        String key = ChatColor.RESET + "" + ChatColor.YELLOW;
        String value = ChatColor.RESET + "" + ChatColor.AQUA;

        message.append(ChatColor.YELLOW + "" + ChatColor.BOLD + "====== Clan Info ======\n");
        message.append(key + "Name: " + value + clan.getName() + "\n");
        message.append(key + "Owner: " + value + owner.getCachedUsername() + "\n");
        message.append(key + "Members: " + value);

        StringJoiner memberJoiner = new StringJoiner(", ");
        for(String member : clan.getMembers()) {
            memberJoiner.add(PlayerManager.getInstance().getPlayer(member).getCachedUsername());
        }

        message.append(memberJoiner.length() > 0 ? memberJoiner.toString() + "\n" : "None" + "\n");
        message.append(key + "Land claims available: " + value + clan.getClaimableAmount() + "\n");
        message.append(key + "Land claims: " + value + clan.getClaimAmount() + "\n");

        player.sendMessage(message.toString());
        return true;
    }

    @Override
    public String getKeyword() {
        return "info";
    }
}
