package nl.friendscraft.friendscraft.events;

import nl.friendscraft.friendscraft.checks.MaintenanceCheck;
import nl.friendscraft.friendscraft.configs.MaintenanceConfig;
import nl.friendscraft.friendscraft.configs.MessageConfig;
import nl.friendscraft.friendscraft.utils.Debug;
import org.bukkit.event.EventHandler;
import nl.friendscraft.friendscraft.utils.ChatUtil;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import java.util.UUID;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        boolean WhitelistStatus = MaintenanceConfig.whitelistStatus;
        String joinMessage = ChatUtil.format(MessageConfig.playerjoin);
        String playerName = event.getPlayer().getName();
        String formattedJoinMessage = joinMessage.replace("%player%", playerName);

        if (WhitelistStatus) {
            UUID playerUUID = event.getPlayer().getUniqueId();
            if (MaintenanceCheck.checkMaintenance(playerUUID)) {
                event.setJoinMessage(formattedJoinMessage);
                Debug.format(playerName + "staat op de maintenance whitelist en is toegelaten.");
            }
            else {
                event.getPlayer().kickPlayer(MaintenanceConfig.maintenanceKick);
                Debug.format(playerName + "staat niet op de maintenance whitelist en is gekickt.");
            }
            return;
        }

        if (!WhitelistStatus) {
                event.setJoinMessage(formattedJoinMessage);
                Debug.format(playerName + "is gejoined, maintenance uit.");
        }
    }
}