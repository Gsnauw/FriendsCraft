package nl.friendscraft.friendscraft.events;

import nl.friendscraft.friendscraft.checks.MaintenanceCheck;
import nl.friendscraft.friendscraft.configs.MaintenanceConfig;
import nl.friendscraft.friendscraft.configs.MessageConfig;
import nl.friendscraft.friendscraft.utils.Debug;
import org.bukkit.event.EventHandler;
import nl.friendscraft.friendscraft.utils.ChatUtil;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
                event.getPlayer().kickPlayer(MessageConfig.maintenanceKick);
                Debug.format(playerName + "staat niet op de maintenance whitelist en is gekickt.");
            }
            return;
        }

        if (!WhitelistStatus) {
                event.setJoinMessage(formattedJoinMessage);
                Debug.format(playerName + "is gejoined, maintenance uit.");
            return;
        }

        //haal de lijst uit de config (als String)
        List<String> whitelist = MaintenanceConfig.whitelist;


        //haal lijst om en zet hem om naar UUID
        List<UUID> whitelistUUID = MaintenanceConfig.whitelist.stream()
                .map(u -> UUID.fromString(u))
                .collect(Collectors.toList());

        //zet middelste lijst weer terug naar String
        List<String> whitelistString = whitelistUUID.stream()
                .map(u -> u.toString())
                .collect(Collectors.toList());


        //lijst moet wel String zijn,
        MaintenanceConfig.save("maintenance.whitelisted",whitelistString);

    }
}