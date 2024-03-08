package nl.friendscraft.friendscraft;

import com.earth2me.essentials.IEssentials;
import nl.friendscraft.friendscraft.commands.AdminCommand;
import nl.friendscraft.friendscraft.commands.MaintenanceCommand;
import nl.friendscraft.friendscraft.configs.MaintenanceConfig;
import nl.friendscraft.friendscraft.configs.MessageConfig;
import nl.friendscraft.friendscraft.events.*;
import nl.friendscraft.friendscraft.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import nl.friendscraft.friendscraft.configs.DefaultConfig;

import java.util.logging.Level;

public final class FriendsCraft extends JavaPlugin {

    private DefaultConfig defaultConfig;
    private MessageConfig messageConfig;
    private MaintenanceConfig maintenanceConfig;
    static FriendsCraft friendsCraft;

    private IEssentials essentials;

    @Override
    public void onEnable() {
        friendsCraft = this;

        defaultConfig = new DefaultConfig("config.yml",this);
        messageConfig = new MessageConfig("message.yml",this);
        maintenanceConfig = new MaintenanceConfig("maintenance.yml",this);

        if (statusEssentials()) {
            this.essentials = (IEssentials) Bukkit.getPluginManager().getPlugin("Essentials");
            ChatUtil.sendConsolePrefixInfo("Connectie met Essentials gemaakt.");
        }
        if (!statusEssentials()) {
            ChatUtil.sendConsolePrefixError("Essentials plugin niet gevonden. Maintenance commands add, remove en list uitschakelen...");
        }

        this.getCommand("friendscraftadmin").setExecutor(new AdminCommand());
        this.getCommand("maintenance").setExecutor(new MaintenanceCommand());
        this.getServer().getPluginManager().registerEvents(new DoubleShulkerShells(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerQuit(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerLogin(), this);
        this.getServer().getPluginManager().registerEvents(new MOTD(), this);

        Bukkit.getServer().getLogger().info("[Friends-Craft] Plugin enabled, Hello World");
    }

    public boolean statusEssentials() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Essentials") != null) {
            return true;
        }
        return false;
    }

    @Override
    public void onDisable() {
        Bukkit.getServer().getLogger().info("[Friends-Craft] Plugin disabled, Bye!");
    }

    public void reload() {
        defaultConfig.reload();
        messageConfig.reload();
        maintenanceConfig.reload();
    }

    public IEssentials getEssentials() {
        return this.essentials;
    }

    public void reloadMaintenance() {
        maintenanceConfig.reload();
    }

    public static FriendsCraft getInstance() {
        return FriendsCraft.friendsCraft;
    }
}