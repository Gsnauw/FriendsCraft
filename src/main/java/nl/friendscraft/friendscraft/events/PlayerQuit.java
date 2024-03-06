package nl.friendscraft.friendscraft.events;

import nl.friendscraft.friendscraft.configs.MessageConfig;
import nl.friendscraft.friendscraft.utils.Debug;
import org.bukkit.event.EventHandler;
import nl.friendscraft.friendscraft.utils.ChatUtil;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit  implements Listener {

        @EventHandler
        public void onPlayerQuit(PlayerQuitEvent event) {
            String quitMessage = ChatUtil.format(MessageConfig.playerquit);
            String playerName = event.getPlayer().getName();
            String formattedQuitMessage = quitMessage.replace("%player%", playerName);
            event.setQuitMessage(formattedQuitMessage);
            Debug.format("Quit: " + playerName + " heeft de server verlaten.");
        }
}
