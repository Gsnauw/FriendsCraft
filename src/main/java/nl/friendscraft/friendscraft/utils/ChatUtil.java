package nl.friendscraft.friendscraft.utils;

import nl.friendscraft.friendscraft.configs.MessageConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class ChatUtil {
    public static String format(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String formatprefix(String text) {
        return format(MessageConfig.prefix + text);
    }

    public static String noPermission() {
        return formatprefix(MessageConfig.geenpermissie);
    }

    public static void playerCommand() {
        Bukkit.getServer().getLogger().info("Je moet een speler zijn om deze actie uit te voeren.");
    }
    public static String consoleCommand() {
        return format(MessageConfig.prefix + "Deze actie kan alleen console uitvoeren.");
    }

    public static void sendConsole(String text) {
        Bukkit.getServer().getLogger().info(text);
    }

    static String ConsolePrefix = "[Friends-Craft] ";
    public static void sendConsolePrefixInfo(String text) {
        Bukkit.getServer().getLogger().info(ConsolePrefix + text);
    }
    public static void sendConsolePrefixWarn(String text) {
        Bukkit.getServer().getLogger().warning(ConsolePrefix + text);
    }
    public static void sendConsolePrefixError(String text) {
        Bukkit.getServer().getLogger().severe(ConsolePrefix + text);
    }
}
