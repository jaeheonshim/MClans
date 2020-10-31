package com.jaeheonshim.simplysurvival.mclans.tasks;

import com.jaeheonshim.simplysurvival.mclans.Clan;
import com.jaeheonshim.simplysurvival.mclans.ClanManager;
import com.jaeheonshim.simplysurvival.mclans.Constant;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class IncrementServerClaimTask implements Runnable {
    private long lastTick = System.currentTimeMillis();

    @Override
    public void run() {
        Set<Clan> notedClans = new HashSet<>();
        for(Player player : Bukkit.getOnlinePlayers()) {
            Clan clan = ClanManager.getClanManager().getClanOfPlayer(player.getUniqueId().toString());
            if(clan != null && !notedClans.contains(clan)) {
                notedClans.add(clan);
                if(clan.incrementOnlineTimer(System.currentTimeMillis() - lastTick) >= Constant.CLAN_EARN_CLAIM_TIME) {
                    clan.incrementClaimableAmount();
                    clan.resetOnlineTimer();
                    clan.broadcastToOnline(ChatColor.GREEN + "Your clan has been granted a land claim!");
                }

                ClanManager.getClanManager().saveClan(clan);
            }
        }
    }
}
