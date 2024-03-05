package nl.friendscraft.friendscraft.events;

import nl.friendscraft.friendscraft.configs.MessageConfig;
import nl.friendscraft.friendscraft.utils.Debug;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import nl.friendscraft.friendscraft.utils.ChatUtil;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit  implements Listener {

        private String quitMessage = ChatUtil.format(MessageConfig.playerquit);

        @EventHandler
        public void onPlayerQuit(PlayerQuitEvent event) {
            Debug.format("Start quit");
            String playerName = event.getPlayer().getName();
            String formattedQuitMessage = quitMessage.replace("%player%", playerName);
            Debug.format("Quit naam: " + playerName);
            event.setQuitMessage(formattedQuitMessage);
            Debug.format("Quit einde: " + playerName + " heeft de server verlaten.");
        }
}
