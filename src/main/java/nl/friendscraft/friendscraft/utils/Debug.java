package nl.friendscraft.friendscraft.utils;

import nl.friendscraft.friendscraft.configs.DefaultConfig;
import org.bukkit.Bukkit;

public class Debug {

    public static void format(String text) {
        if (DefaultConfig.debug) {
            String formated = "[Friends-Craft Debug] " + text;
            Bukkit.getServer().getLogger().info(formated);
        }
    }
}
