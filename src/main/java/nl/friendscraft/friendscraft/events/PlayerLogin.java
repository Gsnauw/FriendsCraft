package nl.friendscraft.friendscraft.events;

import nl.friendscraft.friendscraft.checks.MaintenanceCheck;
import nl.friendscraft.friendscraft.configs.MaintenanceConfig;
import nl.friendscraft.friendscraft.utils.Debug;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.UUID;

public class PlayerLogin implements Listener {

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {

        boolean WhitelistStatus = MaintenanceConfig.whitelistStatus;
        String playerName = event.getPlayer().getName();

        if (WhitelistStatus) {
            UUID playerUUID = event.getPlayer().getUniqueId();
            if (MaintenanceCheck.checkMaintenance(playerUUID)) {
                Debug.format(playerName + " staat op de maintenance whitelist en is toegelaten.");
            }
            if (!MaintenanceCheck.checkMaintenance(playerUUID)) {
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, MaintenanceConfig.maintenanceKick);
                Debug.format(playerName + " staat niet op de maintenance whitelist en is gekickt.");
            }
        }
    }
}
