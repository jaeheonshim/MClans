package com.jaeheonshim.simplysurvival;

import com.jaeheonshim.simplysurvival.mclans.*;
import com.jaeheonshim.simplysurvival.mclans.commands.*;
import com.jaeheonshim.simplysurvival.server.PlayerManager;
import com.jaeheonshim.simplysurvival.server.listeners.PlayerChatListener;
import com.mongodb.MongoClient;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public class SimplySurvivalPlugin extends JavaPlugin {
    private static List<AbstractCommand> commands = Arrays.asList(
            new ClaimCommand(),
            new InfoCommand(),
            new NewClanCommand(),
            new ListClaimsCommand(),
            new UnclaimCommand(),
            new InvitePlayerCommand(),
            new AcceptInviteCommand(),
            new DestroyClanCommand(),
            new KickMemberCommand(),
            new LeaveClanCommand()
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


        getServer().getPluginManager().registerEvents(new PlayerChatListener(), this);

        Morphia morphia = new Morphia();

        morphia.mapPackage("com.jaeheonshim.simplysurvival.mclans");
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