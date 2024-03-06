package nl.friendscraft.friendscraft.commands;

import nl.friendscraft.friendscraft.FriendsCraft;
import nl.friendscraft.friendscraft.configs.MaintenanceConfig;
import nl.friendscraft.friendscraft.events.MaintenanceKick;
import nl.friendscraft.friendscraft.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MaintenanceCommand implements CommandExecutor {

    String commandHelp = "/fcm <enable, disable, help, status, list, add, remove>";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            sender.sendMessage(ChatUtil.formatprefix("&cOnjuist commando, gebruik: " + commandHelp));
            return false;
        }

        if (args.length == 1) {
            switch (args[0]) {
                case "enable":
                    if (sender.hasPermission("friendscraft.maintenance.enable")) {
                        MaintenanceConfig.save("whitelist", true);
                        FriendsCraft.getInstance().reloadMaintenance();
                        MaintenanceKick.enable();
                        sender.sendMessage(ChatUtil.formatprefix("&9De maintenance mode is ingeschakelt."));
                        break;
                    }
                case "help":
                    if (sender.hasPermission("friendscraft.help")) {
                        sender.sendMessage(ChatUtil.formatprefix("&9Gebruik: " + commandHelp));
                        break;
                    }
                case "list":
                    if (sender.hasPermission("friendscraft.maintenance.list")) {
                        List<UUID> whitelistUUID = MaintenanceConfig.whitelist.stream()
                                .map(u -> UUID.fromString(u))
                                .collect(Collectors.toList());

                        if (!(whitelistUUID == null)) {
                            List<String> playerNames = new ArrayList<>();
                            for (UUID uuid : whitelistUUID) {
                                String playerName = Bukkit.getOfflinePlayer(uuid).getName();
                                if (playerName != null) {
                                    playerNames.add(playerName);
                                }
                            }
                            sender.sendMessage("Spelers: " + String.join(", ", playerNames));
                            String playerNamesMessage = playerNames.stream()
                                    .limit(playerNames.size() - 1) // Exclude the last element
                                    .collect(Collectors.joining("&a, &b")) // Join elements with comma and space delimiter
                                    + (playerNames.size() > 1 ? "&a en &b" : "") // Add " en " before the last element if there's more than one element
                                    + playerNames.get(playerNames.size() - 1); // Add the last element

                            ChatUtil.formatprefix("&9Deze spelers staan op de maintenance whitelist: " + playerNamesMessage + "&9.");

                            break;
                        }
                        sender.sendMessage("de lijst is leeg");
                }
                default:
                    sender.sendMessage(ChatUtil.formatprefix("&cOnjuist commando, gebruik: " + commandHelp));
                    break;
            }
        }
        return false;
    }
}
