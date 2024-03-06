package nl.friendscraft.friendscraft.events;

import nl.friendscraft.friendscraft.configs.MaintenanceConfig;
import nl.friendscraft.friendscraft.checks.MaintenanceCheck;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.List;

public class MaintenanceKick {
    public static void enable() {
        List<Player> onlinePlayers = (List<Player>) Bukkit.getOnlinePlayers();
        for (Player player : onlinePlayers) {
            if (!MaintenanceCheck.checkMaintenance(player.getUniqueId())) {
                player.kickPlayer(MaintenanceConfig.maintenanceOnlineKick);
            }
        }
    }
}
