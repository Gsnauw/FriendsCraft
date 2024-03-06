package nl.friendscraft.friendscraft.commands;

import nl.friendscraft.friendscraft.configs.MaintenanceConfig;
import nl.friendscraft.friendscraft.events.MaintenanceKick;
import nl.friendscraft.friendscraft.utils.ChatUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MaintenanceCommand implements CommandExecutor {

    String commandHelp = "/fcm <enable, disable, help, status, list, add, remove>";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)) {
            ChatUtil.playerCommand();
            return false;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatUtil.formatprefix("&cOnjuist commando, gebruik: " + commandHelp));
            return false;
        }

        if (args.length == 1) {
            switch (args[0]) {
                case "enable":
                    if (sender.hasPermission("friendscraft.maintenance.enable")) {
                        MaintenanceConfig.save("whitelist", true);
                        MaintenanceKick.enable();
                        sender.sendMessage(ChatUtil.formatprefix("&9De maintenance mode is ingeschakelt."));
                        break;
                    }
                case "help":
                    if (sender.hasPermission("friendscraft.help")) {
                        sender.sendMessage(ChatUtil.formatprefix("&9Gebruik: /fca <reload, help>"));
                        break;
                    }
                default:
                    sender.sendMessage(ChatUtil.formatprefix("&cOnjuist commando, gebruik: " + commandHelp));
                    break;
            }
        }
        return false;
    }
}
