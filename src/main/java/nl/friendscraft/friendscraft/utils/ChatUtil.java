package nl.friendscraft.friendscraft.utils;

import nl.friendscraft.friendscraft.configs.MessageConfig;
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

    public static String playerCommand() {
        return format("&cJe moet een speler zijn om deze actie uit te voeren.");
    }
}
