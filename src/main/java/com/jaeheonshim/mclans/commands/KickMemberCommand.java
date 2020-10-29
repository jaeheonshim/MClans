package com.jaeheonshim.mclans.commands;

import com.jaeheonshim.mclans.Clan;
import com.jaeheonshim.mclans.ClanManager;
import com.jaeheonshim.mclans.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KickMemberCommand extends AbstractCommand {
    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        ClanManager manager = ClanManager.getClanManager();
        Player player = ((Player) sender);
        if(manager.getClanOfPlayer(player.getUniqueId().toString()) == null) {
            player.sendMessage(ChatColor.RED + "You aren't in a clan!");
            return true;
        }

        Clan clan = manager.getClanOfPlayer(player.getUniqueId().toString());
        if(!clan.getOwnerUuid().equalsIgnoreCase(player.getUniqueId().toString())) {
            player.sendMessage(ChatColor.RED + "You must be the clan owner in order to perform this action.");
            return true;
        }

        if(args.length < 2) {
            player.sendMessage(ChatColor.RED + "Please specify the username of the player you want to kick");
            return true;
        }

        String playerUsername = args[1];
        String kickUuid = null;

        for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if(onlinePlayer.getName().equalsIgnoreCase(playerUsername)) {
                kickUuid = onlinePlayer.getUniqueId().toString();
            }
        }

        if(kickUuid == null) {
            if((kickUuid = PlayerManager.getInstance().getCachedUuid(playerUsername)) == null) {
                player.sendMessage(ChatColor.RED + "That player could not be found!");
                return true;
            }
        }

        if(kickUuid.equalsIgnoreCase(clan.getOwnerUuid())) {
            player.sendMessage(ChatColor.RED + "You can't kick the owner of a clan!");
            return true;
        }

        if(clan.removeMember(kickUuid))
            clan.broadcastToOnline(ChatColor.YELLOW + playerUsername + ChatColor.RED + " has been removed from your clan.");
        else
            player.sendMessage(ChatColor.RED + "That player isn't a member of your clan!");
        return true;
    }

    @Override
    public String getKeyword() {
        return "kick";
    }
}
