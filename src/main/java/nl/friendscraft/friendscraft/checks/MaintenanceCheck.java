package nl.friendscraft.friendscraft.checks;

import nl.friendscraft.friendscraft.configs.MaintenanceConfig;
import nl.friendscraft.friendscraft.utils.Debug;
import org.bukkit.Bukkit;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MaintenanceCheck {

    public static boolean checkMaintenance(UUID playerUUID) {

        List<String> uuidString = MaintenanceConfig.whitelist;
            List<UUID> whitelistUUID = MaintenanceConfig.whitelist.stream()
                    .map(u -> UUID.fromString(u))
                    .collect(Collectors.toList());
            Debug.format(whitelistUUID.toString());

            for (UUID blk : whitelistUUID) {
                if (blk.equals(playerUUID)) {
                    return true;
                }
            }
        return false;
    }
}
