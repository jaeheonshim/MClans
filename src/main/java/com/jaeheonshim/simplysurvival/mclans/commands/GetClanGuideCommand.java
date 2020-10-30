package com.jaeheonshim.simplysurvival.mclans.commands;

import com.jaeheonshim.simplysurvival.mclans.ClansGuideBook;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetClanGuideCommand extends AbstractClanCommand {
    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = ((Player) sender).getPlayer();

            player.getInventory().addItem(ClansGuideBook.getBook());
        }
        return true;
    }

    @Override
    public String getKeyword() {
        return "guide";
    }
}
