package nl.friendscraft.friendscraft.commands;

import nl.friendscraft.friendscraft.FriendsCraft;
import nl.friendscraft.friendscraft.utils.ChatUtil;
import nl.friendscraft.friendscraft.utils.Debug;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminCommand implements CommandExecutor {

    private final FriendsCraft mainclass;

    public AdminCommand(FriendsCraft mainclass) {
        this.mainclass = mainclass;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 0) {
                sender.sendMessage(ChatUtil.formatprefix("&cOnjuist commando, gebruik: /fca <reload, help>"));
                return false;
            }

            if (args.length == 1) {
                switch (args[0]) {
                    case "reload":
                        if (sender.hasPermission("friendscraft.admin.reload")) {
                            this.mainclass.reloadconfig();
                            Debug.format("Plugin reload Command.");
                            sender.sendMessage(ChatUtil.formatprefix("&9De plugin is gereload."));
                            break;
                        }
                    case "help":
                        if (sender.hasPermission("friendscraft.user")) {
                            sender.sendMessage(ChatUtil.formatprefix("&0Gebruik: /fca <reload, help>"));
                            break;
                        }
                }
            }
        }
        else {
            ChatUtil.playerCommand();
        }
        return false;
    }
}