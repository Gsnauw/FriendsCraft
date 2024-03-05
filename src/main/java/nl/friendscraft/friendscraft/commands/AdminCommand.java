package nl.friendscraft.friendscraft.commands;

import nl.friendscraft.friendscraft.FriendsCraft;
import nl.friendscraft.friendscraft.configs.DefaultConfig;
import nl.friendscraft.friendscraft.configs.MessageConfig;
import nl.friendscraft.friendscraft.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class AdminCommand implements CommandExecutor {

    private final FriendsCraft mainclass;

    public AdminCommand(FriendsCraft mainclass) {
        this.mainclass = mainclass;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 1) {
            switch (args[0]) {
                case "reload":
                        this.mainclass.reloadconfig();
                        Bukkit.getServer().getLogger().info("reloaded");
                        break;
            }
        }

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 0) {
                sender.sendMessage(ChatUtil.formatprefix("&cOnjuost commando, gebruik: /fca <reload, help>"));
                return false;
            }

            if (args.length == 1) {
                switch (args[0]) {
                    case "reload":
                        if (sender.hasPermission("friendscraft.admin.reload")) {
                            this.mainclass.reloadconfig();
                            sender.sendMessage(ChatUtil.formatprefix("&9De plugin is gereload."));
                            break;
                        }
                    case "help":
                        if (sender.hasPermission("friendscraft.user")) {
                            sender.sendMessage("Help command basis");
                            break;
                        }
                }
            }
        }
        else {
            Bukkit.getServer().getLogger().info(ChatUtil.playerCommand());
        }
        return false;
    }
}