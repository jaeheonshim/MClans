package com.jaeheonshim.simplysurvival.server.commands;

import com.jaeheonshim.simplysurvival.server.PlayerManager;
import com.jaeheonshim.simplysurvival.server.SPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.Duration;

public class MuteCommand extends AbstractServerCommand {
    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        if(args.length < 2) {
            return false;
        }

        String username = args[0];
        String[] duration = args[1].trim().split("\\|");
        String muteUuid = null;

        for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if(onlinePlayer.getName().equalsIgnoreCase(username)) {
                muteUuid = onlinePlayer.getUniqueId().toString();
            }
        }

        if(muteUuid == null) {
            if((muteUuid = PlayerManager.getInstance().getCachedUuid(username)) == null) {
                sender.sendMessage(ChatColor.RED + "That player could not be found!");
                return true;
            }
        }

        SPlayer mutePlayer = PlayerManager.getInstance().getPlayer(muteUuid);
        mutePlayer.setMutedDuration(Duration.ofHours(Integer.parseInt(duration[0])).plusMinutes(Integer.parseInt(duration[1])), args.length == 3 ? args[2] : "[NO REASON SPECIFIED]");

        sender.sendMessage("Muted " + username);

        return true;
    }

    @Override
    public String getKeyword() {
        return "mute";
    }
}
