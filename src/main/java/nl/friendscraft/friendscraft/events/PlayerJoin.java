package nl.friendscraft.friendscraft.events;

import nl.friendscraft.friendscraft.configs.MessageConfig;
import nl.friendscraft.friendscraft.utils.Debug;
import org.bukkit.event.EventHandler;
import nl.friendscraft.friendscraft.utils.ChatUtil;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin  implements Listener {

    private String joinMessage = ChatUtil.format(MessageConfig.playerjoin);

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Debug.format("Start join");
        String playerName = event.getPlayer().getName();
        String formattedJoinMessage = joinMessage.replace("%player%", playerName);
        Debug.format("Join naam: " + playerName);
        event.setJoinMessage(formattedJoinMessage);
        Debug.format("Join einde: " + playerName + " is gejoined");
    }
}
