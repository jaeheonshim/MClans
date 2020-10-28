package com.jaeheonshim.mclans;

import com.jaeheonshim.mclans.commands.*;
import com.mongodb.MongoClient;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.logging.Level;

public class MClansPlugin extends JavaPlugin {
    private static List<AbstractCommand> commands = Arrays.asList(
            new ClaimCommand(),
            new InfoCommand(),
            new NewClanCommand(),
            new ListClaimsCommand(),
            new UnclaimCommand()
    );

    @Override
    public void onEnable() {
        saveDefaultConfig();

        getLogger().info("Plugin enabled");

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(), this);
        getServer().getPluginManager().registerEvents(new PreventedActionListener(), this);
        getServer().getPluginManager().registerEvents(new ChestOpenListener(), this);
        Morphia morphia = new Morphia();

        morphia.mapPackage("com.jaeheonshim.mclans");
        Datastore datastore = morphia.createDatastore(new MongoClient(), "mclans");
        datastore.ensureIndexes();

        PlayerManager.init(datastore);
        ClanManager.init(datastore);

        try {
            ClanManager.getClanManager().loadClans();
        } catch(RuntimeException e) {
            Bukkit.getLogger().severe("Failed to load all clans! Disabling plugin to preserve database.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
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
