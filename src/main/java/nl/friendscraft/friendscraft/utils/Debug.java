package nl.friendscraft.friendscraft.utils;

import nl.friendscraft.friendscraft.configs.DefaultConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Debug {

    static boolean debug = DefaultConfig.debug;
    public static void format(String text) {
        if (debug) {
            String formated = "[Debug] " + text;
            Bukkit.getServer().getLogger().info(formated);
        }
    }
}
