package nl.friendscraft.friendscraft.events;

import nl.friendscraft.friendscraft.configs.DefaultConfig;
import nl.friendscraft.friendscraft.configs.MaintenanceConfig;
import nl.friendscraft.friendscraft.utils.ChatUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class MOTD implements Listener {
    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {
        if (MaintenanceConfig.whitelistStatus) {
            event.setMotd(ChatUtil.format(DefaultConfig.maintenanceMotd1 +"\n&7" + DefaultConfig.maintenanceMotd2));
            return;
        }
        event.setMotd(ChatUtil.format(DefaultConfig.motd1 +"\n" + DefaultConfig.motd2));
    }
}
