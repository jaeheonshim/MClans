package com.jaeheonshim.simplysurvival.mclans.commands;

import com.jaeheonshim.simplysurvival.mclans.Clan;
import com.jaeheonshim.simplysurvival.mclans.ClanManager;
import com.jaeheonshim.simplysurvival.mclans.DataChunk;
import com.jaeheonshim.simplysurvival.server.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListClaimsCommand extends AbstractCommand {
    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        ClanManager manager = ClanManager.getClanManager();
        PlayerManager playerManager = PlayerManager.getInstance();
        Player player = ((Player) sender);

        Clan clan;

        if((clan = manager.getClanOfPlayer(player.getUniqueId().toString())) == null) {
            player.sendMessage(ChatColor.RED + "You aren't in a clan!");
            return true;
        }

        StringBuilder response = new StringBuilder(ChatColor.YELLOW + "======= Claimed Chunks =======\n" + ChatColor.RED);
        for(DataChunk chunk : clan.getLandClaims()) {
            response.append(chunk.getChunk().getWorld().getEnvironment().name() + " - " + chunk.getChunk().getX() + ", " + chunk.getChunk().getZ());
            response.append("\n");
        }

        player.sendMessage(response.toString());
        return true;
    }

    @Override
    public String getKeyword() {
        return "claims";
    }
}
