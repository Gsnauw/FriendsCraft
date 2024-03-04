package nl.friendscraft.friendscraft.events;

import nl.friendscraft.friendscraft.configs.MessageConfig;
import org.bukkit.event.EventHandler;
import nl.friendscraft.friendscraft.utils.ChatUtil;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit  implements Listener {

        private String quitMessage = ChatUtil.format(MessageConfig.playerquit);

        @EventHandler
        public void onPlayerQuit(PlayerQuitEvent event) {
            String playerName = event.getPlayer().getName();
            String formattedQuitMessage = quitMessage.replace("%player%", playerName);
            event.setQuitMessage(formattedQuitMessage);
        }
}
