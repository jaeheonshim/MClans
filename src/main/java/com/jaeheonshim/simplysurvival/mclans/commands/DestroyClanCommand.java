package com.jaeheonshim.simplysurvival.mclans.commands;

import com.jaeheonshim.simplysurvival.mclans.Clan;
import com.jaeheonshim.simplysurvival.mclans.ClanManager;
import com.jaeheonshim.simplysurvival.server.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DestroyClanCommand extends AbstractCommand {
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

        if(args.length == 2) {
            if(args[1].equalsIgnoreCase("confirm")) {
                if(PlayerManager.getInstance().getPlayer(player.getUniqueId().toString()).isConfirmDestroy()) {
                    manager.destroyClan(clan);
                    player.sendMessage(ChatColor.GREEN + "You have destroyed the clan " + clan.getName());
                    return true;
                } else {
                    player.sendMessage(ChatColor.RED + "Run the command /clan destroy first.");
                    return true;
                }
            }
        }

        player.sendMessage(ChatColor.RED + "YOU ARE ABOUT TO PERMANENTLY DESTROY YOUR CLAN\n" +
                "==============================================\n\n\n" +
                "Destroying your clan will:\n" + ChatColor.YELLOW +
                "- Remove all its members\n" +
                "- Delete all of its land claims\n" +
                "- Remove all of its earned land claims\n" +
                "- Free up its name to be used by other clans\n\n" + ChatColor.RED +
                "If you are absolutely sure you want to perform this action, type " + ChatColor.AQUA + "/clan destroy confirm" + ChatColor.RED + " within the next 15 seconds.");

        PlayerManager.getInstance().getPlayer(player.getUniqueId().toString()).queueDestroy();

        return true;
    }

    @Override
    public String getKeyword() {
        return "destroy";
    }
}
