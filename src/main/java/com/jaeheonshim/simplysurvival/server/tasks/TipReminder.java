package com.jaeheonshim.simplysurvival.server.tasks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public class TipReminder implements Runnable {
    private List<String> reminds = Arrays.asList(
            ChatColor.BLUE + "Need more land claims for your clan? Vote for our server with " + ChatColor.YELLOW + "/vote" + ChatColor.BLUE + "! 15 votes = 1 landclaim"
    );

    int it;

    @Override
    public void run() {
        Bukkit.getServer().broadcastMessage(reminds.get(it));
        if(it + 1 >= reminds.size()) {
            it = 0;
        } else {
            it++;
        }
    }
}
