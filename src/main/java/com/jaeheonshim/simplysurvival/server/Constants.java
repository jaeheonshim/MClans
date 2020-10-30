package com.jaeheonshim.simplysurvival.server;

import com.jaeheonshim.simplysurvival.server.domain.MessageSequence;
import org.bukkit.ChatColor;

public class Constants {
    public static final MessageSequence welcomeSequence = MessageSequence.start(ChatColor.BLUE + "Welcome to SimplySurvival!").addNext(ChatColor.BLUE + "SimplySurvival is a survival minecraft server that is focused purely on the vanilla aspect of gameplay. This means" + ChatColor.YELLOW + " no tpa/sethome commands, nor any ranks or boosts" + ChatColor.BLUE + " that give other players a gameplay advantage.").addNext(ChatColor.BLUE + "For the convenience of players, a land claiming system similar to factions has been custom developed and implemented. This system allows you to claim parts of the world in chunk segments. These chunks will be protected from griefing by players outside of your clan.").addNext(ChatColor.BLUE + "However, note that" + ChatColor.GOLD + " effects outside your claims can still affect your claim, such as explosions." + ChatColor.BLUE + " Therefore, having a strong outer defense for your claims is recommended.").addNext(ChatColor.BLUE + "A area with a 5 chunk radius around spawn is protected from modification and claims. Therefore, you'll have to travel out away from spawn before you can claim your own chunks.").addNext(ChatColor.BLUE + "To make life for new players easier, you are protected from hunger and pvp for the next" + ChatColor.GOLD + " 60 minutes" + ChatColor.BLUE +". Use this time to find a suitable place to settle. Note that once these 60 minutes are over " + ChatColor.RED + "you will be vulnerable to other players!").addNext(ChatColor.BLUE + "Finally, here are the rules of the server. Make sure to read and memorize them, as any" + ChatColor.RED + " infractions can result in a ban." + ChatColor.YELLOW + "\n-" + ChatColor.BLUE + " No hacking - This includes the use of x-ray. Basically anything that gives you an unfair advantage over others.\n" + ChatColor.YELLOW + "-" + ChatColor.BLUE + " Be friendly in chat. Profanity and banter is allowed, but harassment/threats against other players isn't. Also, try to limit the discussion of controversial subjects such as religion and politics.\n" + ChatColor.YELLOW + "-" + ChatColor.BLUE + " No spawn killing: you shouldn't kill players straight after they respawn.").addNext(ChatColor.BLUE + "That's it! Enjoy your time on the server. You can join our official Discord server here:" + ChatColor.GREEN + " https://discord.gg/q7rc6RU" + ChatColor.BLUE + ". Thanks for playing!").getStart();
}
