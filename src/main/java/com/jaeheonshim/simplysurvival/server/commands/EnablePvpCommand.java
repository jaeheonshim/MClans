package com.jaeheonshim.simplysurvival.server.commands;

import com.jaeheonshim.simplysurvival.server.PlayerManager;
import com.jaeheonshim.simplysurvival.server.SPlayer;
import com.jaeheonshim.simplysurvival.server.domain.PlayerProperties;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EnablePvpCommand extends AbstractServerCommand {
    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            SPlayer player = PlayerManager.getInstance().getPlayer(((Player) sender).getUniqueId().toString());

            if(!player.getProperty(PlayerProperties.ENABLED_PVP).equals("true") && player.getTimePlayed() < 60) {
                player.saveProperty(PlayerProperties.ENABLED_PVP, true);

                PlayerManager.getInstance().savePlayer(player);

                sender.sendMessage(ChatColor.RED + "You have disabled your pvp protection. You may now attack others, but others can also attack you!");
            } else {
                sender.sendMessage(ChatColor.RED + "You do not currently have any pvp protection!");
            }
        }

        return true;
    }

    @Override
    public String getKeyword() {
        return "enablepvp";
    }
}
