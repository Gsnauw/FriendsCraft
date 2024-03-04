package nl.friendscraft.friendscraft.events;

import nl.friendscraft.friendscraft.configs.MessageConfig;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import nl.friendscraft.friendscraft.utils.ChatUtil;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit  implements Listener {

        private String quitMessage = ChatUtil.format(MessageConfig.playerquit);

        @EventHandler
        public void onPlayerQuit(PlayerQuitEvent event) {
            Bukkit.getServer().getLogger().info("Start quit");
            String playerName = event.getPlayer().getName();
            String formattedQuitMessage = quitMessage.replace("%player%", playerName);
            Bukkit.getServer().getLogger().info("Quit naam: " + playerName);
            event.setQuitMessage(formattedQuitMessage);
            Bukkit.getServer().getLogger().info("Quit einde: " + playerName + " heeft de server verlaten.");
        }
}
