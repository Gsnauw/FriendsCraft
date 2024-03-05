package nl.friendscraft.friendscraft.commands;

import nl.friendscraft.friendscraft.FriendsCraft;
import nl.friendscraft.friendscraft.configs.DefaultConfig;
import nl.friendscraft.friendscraft.configs.MessageConfig;
import nl.friendscraft.friendscraft.utils.ChatUtil;
import nl.friendscraft.friendscraft.utils.Debug;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminCommand implements CommandExecutor {

    String commandHelp = "/fca <reload, help>";

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
                    case "reload":
                        if (sender.hasPermission("friendscraft.admin.reload")) {
                            FriendsCraft.getInstance().reload();
                            System.out.println("debug is: " + DefaultConfig.debug);
                            System.out.println("debug is: " + MessageConfig.playerjoin);
                            System.out.println("debug is: " + MessageConfig.playerquit);
                            sender.sendMessage(ChatUtil.formatprefix("&9De plugin is gereload."));
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