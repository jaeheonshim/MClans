package com.jaeheonshim.simplysurvival.server.commands;

import com.jaeheonshim.simplysurvival.server.PlayerManager;
import com.jaeheonshim.simplysurvival.server.SPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand extends AbstractServerCommand {
    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        SPlayer player = PlayerManager.getInstance().getPlayer(((Player) sender));
        if(player.isRecallEligible()) {
            ((Player) sender).getPlayer().teleport(Bukkit.getServer().getWorlds().get(0).getSpawnLocation());
        } else {
            sender.sendMessage(ChatColor.RED + "You must not take damage for 15 seconds before you warp to spawn!");
        }

        return true;
    }

    @Override
    public String getKeyword() {
        return "spawn";
    }
}
