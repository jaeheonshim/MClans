package com.jaeheonshim.mclans;

import com.jaeheonshim.mclans.commands.AbstractCommand;
import com.jaeheonshim.mclans.commands.ClaimCommand;
import com.jaeheonshim.mclans.commands.InfoCommand;
import com.mongodb.MongoClient;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class MClansPlugin extends JavaPlugin {
    private static List<AbstractCommand> commands = Arrays.asList(
            new ClaimCommand(),
            new InfoCommand()
    );

    @Override
    public void onEnable() {
        saveDefaultConfig();

        getLogger().info("Plugin enabled");

        Clan test = ClanManager.getClanManager().newClan("028fce20-ab39-4bd3-b829-8e027ee6a72b", "Test");
        test.claim(getServer().getWorlds().get(0).getChunkAt(new Location(getServer().getWorlds().get(0), 0, 0, 0)));

        getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(), this);
        getServer().getPluginManager().registerEvents(new PreventedActionListener(), this);
        getServer().getPluginManager().registerEvents(new ChestOpenListener(), this);
        Morphia morphia = new Morphia();

        morphia.mapPackage("com.jaeheonshim.mclans");
        Datastore datastore = morphia.createDatastore(new MongoClient(), "mclans");
        datastore.ensureIndexes();
        datastore.save(test);

        PlayerManager.init(datastore);
        ClanManager.init(datastore);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("clan")) {
            if(args.length < 1) {
                return false;
            }

            String cmd = args[0];
            for(AbstractCommand abstractCommand : commands) {
                if(abstractCommand.getKeyword().equalsIgnoreCase(cmd)) {
                    return abstractCommand.execute(sender, command, label, args);
                }
            }
        }

        return false;
    }
}
