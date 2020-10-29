package com.jaeheonshim.simplysurvival.mclans.commands;

import com.jaeheonshim.simplysurvival.mclans.*;
import com.jaeheonshim.simplysurvival.server.PlayerManager;
import com.jaeheonshim.simplysurvival.server.SPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InvitePlayerCommand extends AbstractCommand {
    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        ClanManager manager = ClanManager.getClanManager();
        Player player = ((Player) sender);
        Clan clanOfPlayer = manager.getClanOfPlayer(player.getUniqueId().toString());
        if(clanOfPlayer == null) {
            player.sendMessage(ChatColor.RED + "You aren't in a clan!");
            return true;
        }

        if(args.length < 2) {
            player.sendMessage(ChatColor.RED + "Please specify who you are trying to invite!");
            return true;
        }

        Player invitedPlayer = null;
        for(Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
            if(onlinePlayer.getName().equalsIgnoreCase(args[1])) {
                invitedPlayer = onlinePlayer;
            }
        }

        if(invitedPlayer == null) {
            player.sendMessage(ChatColor.RED + "A player with the username of " + ChatColor.YELLOW + args[1] + ChatColor.RESET + ChatColor.RED + " was not found.");
            return true;
        }

        SPlayer sPlayer = PlayerManager.getInstance().getPlayer(invitedPlayer.getUniqueId().toString());

        if(sPlayer.isInvited(clanOfPlayer) && !sPlayer.getInvitation(clanOfPlayer).isExpired()) {
            player.sendMessage(ChatColor.RED + "You've already invited that player to your clan! Wait for them to accept, or for the invitation to expire.");
            return true;
        }

        sPlayer.addInvitation(new ClanInvitation(clanOfPlayer.getId().toString(), System.currentTimeMillis(), player.getUniqueId().toString()));
        player.sendMessage(ChatColor.GREEN + args[1] + " has been invited to join your clan " + clanOfPlayer.getName());

        invitedPlayer.sendMessage(ChatColor.YELLOW + "--------------- Clan Invitation ---------------\n" +
                ChatColor.GREEN + player.getName() + ChatColor.YELLOW + " has invited you to join the clan " + ChatColor.GREEN + clanOfPlayer.getName() + ChatColor.YELLOW + "! As a member of their clan, you'll have access to their territory and resources. To accept this clan invitation, send the command " + ChatColor.AQUA + "/clan accept " + clanOfPlayer.getName() + ChatColor.YELLOW + " in the chat!");


        return true;
    }

    @Override
    public String getKeyword() {
        return "invite";
    }
}
