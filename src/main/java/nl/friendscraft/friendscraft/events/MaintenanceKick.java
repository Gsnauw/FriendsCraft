package nl.friendscraft.friendscraft.events;

import nl.friendscraft.friendscraft.configs.MaintenanceConfig;
import nl.friendscraft.friendscraft.checks.MaintenanceCheck;
import nl.friendscraft.friendscraft.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MaintenanceKick {
    public static void enable() {
        List<Player> onlinePlayers = (List<Player>) Bukkit.getOnlinePlayers();
        for (Player player : onlinePlayers) {
            if (!MaintenanceCheck.checkMaintenance(player.getUniqueId())) {
                player.kickPlayer(ChatUtil.format(MaintenanceConfig.maintenanceKick));
            }
        }
    }
    public static void broadcastEnable(Player commandUser) {
        ChatUtil.sendConsolePrefixInfo("De Maintenance mode is ingeschakeld.");
        List<UUID> whitelistUUID = MaintenanceConfig.whitelist.stream()
                .map(u -> UUID.fromString(u))
                .collect(Collectors.toList());

        for (UUID uuid : whitelistUUID) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                List<Player> onlinePlayers = (List<Player>) Bukkit.getOnlinePlayers();
                for (Player onlineSpeler  : onlinePlayers) {
                    if (!(player == commandUser)) {
                        if (player == onlineSpeler) {
                            player.sendMessage(ChatUtil.formatprefix(MaintenanceConfig.broadcastEnable));
                        }
                    }
                }
            }
        }
    }

    public static void broadcastDisable(Player commandUser) {
        ChatUtil.sendConsolePrefixInfo("De Maintenance mode is uitgeschakeld.");
        List<UUID> whitelistUUID = MaintenanceConfig.whitelist.stream()
                .map(u -> UUID.fromString(u))
                .collect(Collectors.toList());

        for (UUID uuid : whitelistUUID) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                List<Player> onlinePlayers = (List<Player>) Bukkit.getOnlinePlayers();
                for (Player onlineSpeler  : onlinePlayers) {
                    if (!(player == commandUser)) {
                        if (player == onlineSpeler) {
                            player.sendMessage(ChatUtil.formatprefix(MaintenanceConfig.broadcastDisable));
                        }
                    }
                }
            }
        }
    }


}
