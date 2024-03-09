package nl.friendscraft.friendscraft.commands;

import nl.friendscraft.friendscraft.FriendsCraft;
import nl.friendscraft.friendscraft.configs.MessageConfig;
import nl.friendscraft.friendscraft.utils.ChatUtil;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AdminCommand implements CommandExecutor, TabCompleter {

    String commandHelp = "/fca &b<reload of help>.";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            if (sender.hasPermission("friendscraft.admin.help")) {
                sender.sendMessage(ChatUtil.formatprefix(MessageConfig.onjuistCommand.replace("%commands%", commandHelp).replace("&b", "")));
                return false;
            }
            sender.sendMessage(ChatUtil.noPermission());
            return false;
        }

            if (args.length == 1) {
                switch (args[0]) {
                    case "reload":
                        if (sender.hasPermission("friendscraft.admin.reload")) {
                            FriendsCraft.getInstance().reload();
                            sender.sendMessage(ChatUtil.formatprefix(MessageConfig.reload));
                            break;
                        }
                        sender.sendMessage(ChatUtil.noPermission());
                        break;
                    case "help":
                    default:
                        if (sender.hasPermission("friendscraft.admin.help")) {
                            sender.sendMessage(ChatUtil.formatprefix(MessageConfig.help.replace("%commands%", commandHelp)));
                            break;
                        }
                        sender.sendMessage(ChatUtil.noPermission());
                        break;
                }
            }
            return false;
        }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 1) {
            return Arrays.asList("help", "reload").stream().filter(p -> p.regionMatches(true, 0, args[0], 0, args[0].length())).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

}