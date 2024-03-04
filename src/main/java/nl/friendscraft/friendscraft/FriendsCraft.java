package nl.friendscraft.friendscraft;

import nl.friendscraft.friendscraft.configs.MessageConfig;
import nl.friendscraft.friendscraft.events.DoubleShulkerShells;
import nl.friendscraft.friendscraft.events.PlayerJoin;
import nl.friendscraft.friendscraft.events.PlayerQuit;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import nl.friendscraft.friendscraft.configs.DefaultConfig;

public final class FriendsCraft extends JavaPlugin {

    @Override
    public void onEnable() {

        DefaultConfig defaultconfig = new DefaultConfig("config.yml",this);
        MessageConfig messageconfig = new MessageConfig("message.yml",this);

        this.getServer().getPluginManager().registerEvents(new DoubleShulkerShells(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerQuit(), this);

        Bukkit.getServer().getLogger().info("[Friends-Craft] Plugin enabled, Hello World");
    }

    @Override
    public void onDisable() {
        Bukkit.getServer().getLogger().info("[Friends-Craft] Plugin disabled, Bye!");
    }
}
