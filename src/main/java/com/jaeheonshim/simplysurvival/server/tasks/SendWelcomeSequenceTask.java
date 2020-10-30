package com.jaeheonshim.simplysurvival.server.tasks;

import com.jaeheonshim.simplysurvival.server.PlayerManager;
import com.jaeheonshim.simplysurvival.server.SPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SendWelcomeSequenceTask implements Runnable {
    @Override
    public void run() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            SPlayer sPlayer = PlayerManager.getInstance().getPlayer(player.getUniqueId().toString());
            if(!sPlayer.isCompletedIntro()) {
                player.sendMessage(sPlayer.getCurrentIntroSequence().getMessage());

                if(sPlayer.getCurrentIntroSequence().getNext() == null) {
                    sPlayer.setCompletedIntro(true);
                    PlayerManager.getInstance().savePlayer(sPlayer);
                } else {
                    sPlayer.setCurrentIntroSequence(sPlayer.getCurrentIntroSequence().getNext());
                }
            }
        }
    }
}
