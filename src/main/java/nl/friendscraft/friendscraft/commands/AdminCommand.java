package nl.friendscraft.friendscraft.commands;

import nl.friendscraft.friendscraft.FriendsCraft;
import nl.friendscraft.friendscraft.configs.DefaultConfig;
import nl.friendscraft.friendscraft.configs.MaintenanceConfig;
import nl.friendscraft.friendscraft.configs.MessageConfig;
import nl.friendscraft.friendscraft.utils.ChatUtil;
import nl.friendscraft.friendscraft.utils.Debug;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class AdminCommand implements CommandExecutor, TabCompleter {

    String commandHelp = "/fca <reload, help>";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)) {

            if (args.length == 1) {
                switch (args[0]) {
                    case "reload":
                        if (sender.hasPermission("friendscraft.admin.reload")) {
                            FriendsCraft.getInstance().reload();
                            ChatUtil.sendConsolePrefix("De plugin is gereload.");
                            break;
                        }
                }
                ChatUtil.playerCommand();
                return false;
            }
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

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 1) {
            return Arrays.asList("reload", "help").stream().filter(p -> p.regionMatches(true, 0, args[0], 0, args[0].length())).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

}