package nl.friendscraft.friendscraft;

import nl.friendscraft.friendscraft.commands.AdminCommand;
import nl.friendscraft.friendscraft.configs.MessageConfig;
import nl.friendscraft.friendscraft.events.DoubleShulkerShells;
import nl.friendscraft.friendscraft.events.PlayerJoin;
import nl.friendscraft.friendscraft.events.PlayerQuit;
import nl.friendscraft.friendscraft.utils.Debug;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import nl.friendscraft.friendscraft.configs.DefaultConfig;

public final class FriendsCraft extends JavaPlugin {

    DefaultConfig defaultConfig;
    MessageConfig messageConfig;

    @Override
    public void onEnable() {

        defaultConfig=   new DefaultConfig("config.yml",this);
        messageConfig=  new MessageConfig("message.yml",this);

        this.getCommand("friendscraftadmin").setExecutor(new AdminCommand(this));
        this.getServer().getPluginManager().registerEvents(new DoubleShulkerShells(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerQuit(), this);

        Bukkit.getServer().getLogger().info("[Friends-Craft] Plugin enabled, Hello World");
    }

    @Override
    public void onDisable() {
        Bukkit.getServer().getLogger().info("[Friends-Craft] Plugin disabled, Bye!");
    }

    public void reload() {
        defaultConfig.reload();
        messageConfig.reload();
        Debug.format("Plugin reload Main class.");
    }

}
