package nl.friendscraft.friendscraft.events;

import nl.friendscraft.friendscraft.configs.MessageConfig;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import nl.friendscraft.friendscraft.utils.ChatUtil;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin  implements Listener {

    private String joinMessage = ChatUtil.format(MessageConfig.playerjoin);

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Bukkit.getServer().getLogger().info("Start join");
        String playerName = event.getPlayer().getName();
        String formattedJoinMessage = joinMessage.replace("%player%", playerName);
        Bukkit.getServer().getLogger().info("Join naam: " + playerName);
        event.setJoinMessage(formattedJoinMessage);
        Bukkit.getServer().getLogger().info("Join einde: " + playerName + " is gejoined");
    }
}
