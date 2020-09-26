package com.jaeheonshim.mclans;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MClansPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("Plugin enabled");

        Clan test = ClanManager.getClanManager().newClan("028fce20-ab39-4bd3-b829-8e027ee6a72b", "Test");
        test.claim(getServer().getWorlds().get(0).getChunkAt(new Location(getServer().getWorlds().get(0), 0, 0, 0)));

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(), this);
        getServer().getPluginManager().registerEvents(new PreventedActionListener(), this);
        getServer().getPluginManager().registerEvents(new ChestOpenListener(), this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("clan")) {
            if(args.length < 1) {
                return false;
            }

            String cmd = args[0];
            if(cmd.equalsIgnoreCase("claim")) {
                ClanManager manager = ClanManager.getClanManager();
                Player player = ((Player) sender);
                if(manager.getClanOfPlayer(player.getUniqueId().toString()) == null) {
                    player.sendMessage(ChatColor.RED + "You aren't in a clan!");
                    return true;
                }

                if(player.getLocation().getWorld().getEnvironment() == World.Environment.THE_END) {
                    player.sendMessage(ChatColor.RED + "You can't claim land here.");
                    return true;
                }

                if(manager.getClanOfPlayer(player.getUniqueId().toString()).claim(player.getLocation().getChunk())) {
                    player.sendMessage(ChatColor.GREEN + "Chunk successfully claimed!");
                } else {
                    player.sendMessage(ChatColor.RED + "This chunk is already claimed!");
                }

                return true;
            }
        }

        return false;
    }
}
