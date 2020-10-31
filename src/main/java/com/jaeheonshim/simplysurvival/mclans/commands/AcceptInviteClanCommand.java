package com.jaeheonshim.simplysurvival.mclans.commands;

import com.jaeheonshim.simplysurvival.mclans.Clan;
import com.jaeheonshim.simplysurvival.mclans.ClanManager;
import com.jaeheonshim.simplysurvival.server.PlayerManager;
import com.jaeheonshim.simplysurvival.server.SPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AcceptInviteClanCommand extends AbstractClanCommand {
    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        ClanManager manager = ClanManager.getClanManager();
        Player player = ((Player) sender);
        Clan clanOfPlayer = manager.getClanOfPlayer(player.getUniqueId().toString());
        if(clanOfPlayer != null) {
            player.sendMessage(ChatColor.RED + "You're already in a clan! You'll have to leave that one before you can join another.");
            return true;
        }

        if(args.length < 2) {
            player.sendMessage(ChatColor.RED + "Please specify the name of the clan whose invitation you are trying to accept.");
            return true;
        }

        SPlayer sPlayer = PlayerManager.getInstance().getPlayer(((Player) sender).getUniqueId().toString());
        Clan invitedClan = ClanManager.getClanManager().getClanByName(args[1]);
        if(sPlayer.isInvited(invitedClan)) {
            if(!sPlayer.getInvitation(invitedClan).isExpired()) {
                invitedClan.broadcastToOnline(ChatColor.YELLOW + player.getName() + ChatColor.GREEN + " has joined your clan");
                invitedClan.addMember(sPlayer.getUuid());
                manager.saveClan(invitedClan);
                player.sendMessage(ChatColor.GREEN + "You are now a member of the clan " + invitedClan.getName() + "!");
                sPlayer.removeInvitation(invitedClan);
                return true;
            } else {
                player.sendMessage(ChatColor.RED + "That invitation has expired! You'll have to ask for another one to be sent.");
                return true;
            }
        } else {
            player.sendMessage(ChatColor.RED + "You haven't been invited to join that clan.");
            return true;
        }
    }

    @Override
    public String getKeyword() {
        return "accept";
    }
}
