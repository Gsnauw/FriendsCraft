package nl.friendscraft.friendscraft.events;

import nl.friendscraft.friendscraft.configs.MessageConfig;
import nl.friendscraft.friendscraft.utils.Debug;
import org.bukkit.event.EventHandler;
import nl.friendscraft.friendscraft.utils.ChatUtil;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin  implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String joinMessage = ChatUtil.format(MessageConfig.playerjoin);
        String playerName = event.getPlayer().getName();
        String formattedJoinMessage = joinMessage.replace("%player%", playerName);
        event.setJoinMessage(formattedJoinMessage);
        Debug.format("Join: " + playerName + " is gejoined.");
    }
}