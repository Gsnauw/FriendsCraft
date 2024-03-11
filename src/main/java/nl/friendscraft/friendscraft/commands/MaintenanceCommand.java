package nl.friendscraft.friendscraft.commands;

import com.earth2me.essentials.User;
import nl.friendscraft.friendscraft.FriendsCraft;
import nl.friendscraft.friendscraft.checks.MaintenanceCheck;
import nl.friendscraft.friendscraft.configs.MaintenanceConfig;
import nl.friendscraft.friendscraft.configs.MessageConfig;
import nl.friendscraft.friendscraft.events.MaintenanceKick;
import nl.friendscraft.friendscraft.utils.ChatUtil;
import nl.friendscraft.friendscraft.gui.MaintenanceGui;
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

public class MaintenanceCommand implements CommandExecutor, TabCompleter {

    String commandHelp = "/fcm &b<add (naam), disable, enable, help, list, remove (naam) of status>.";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if (args.length == 0) {
            sender.sendMessage(ChatUtil.formatprefix(MessageConfig.onjuistCommand.replace("%commands%", commandHelp).replace("&b", "")));
            return false;
        }

        if (args.length == 1) {
            switch (args[0]) {
                case "menu":
                    if (sender.hasPermission("friendscraft.maintenance.menu")) {
                    if (sender instanceof Player) {
                        Player player = (Player) sender;
                        MaintenanceGui.openGui(player);
                    }
                    ChatUtil.playerCommand();
                    break;
                }
                    sender.sendMessage(ChatUtil.noPermission());
                    break;

                case "enable":
                    if (sender.hasPermission("friendscraft.maintenance.enable")) {
                        if (!(MaintenanceConfig.whitelistStatus)) {
                            MaintenanceConfig.save("whitelist", true);
                            FriendsCraft.getInstance().reloadMaintenance();
                            MaintenanceKick.enable();
                            sender.sendMessage(ChatUtil.formatprefix(MaintenanceConfig.enabled));
                            break;
                        }
                        if (MaintenanceConfig.whitelistStatus) {
                            sender.sendMessage(ChatUtil.formatprefix(MaintenanceConfig.enabledAl));
                            break;
                        }
                    }
                    sender.sendMessage(ChatUtil.noPermission());
                    break;

                case "disable":
                    if (sender.hasPermission("friendscraft.maintenance.disable")) {
                        if (MaintenanceConfig.whitelistStatus) {
                            MaintenanceConfig.save("whitelist", false);
                            FriendsCraft.getInstance().reloadMaintenance();
                            sender.sendMessage(ChatUtil.formatprefix(MaintenanceConfig.disabled));
                            break;
                        }
                        if (!(MaintenanceConfig.whitelistStatus)) {
                            sender.sendMessage(ChatUtil.formatprefix(MaintenanceConfig.disabledAl));
                            break;
                        }
                    }
                    sender.sendMessage(ChatUtil.noPermission());
                    break;

                case "list":
                    if (sender.hasPermission("friendscraft.maintenance.list")) {
                        if (FriendsCraft.getInstance().statusEssentials()) {
                            if (MaintenanceConfig.whitelist != null && !MaintenanceConfig.whitelist.isEmpty()) {
                                List<String> playerNames = new ArrayList<>();

                                for (String uuid : MaintenanceConfig.whitelist) {
                                        String naam = Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName();
                                        User player = FriendsCraft.getInstance().getEssentials().getOfflineUser(naam);
                                    if (player != null) {
                                        playerNames.add(naam);
                                    }
                                }

                                if (!playerNames.isEmpty()) {
                                    String playerNamesMessage;
                                    if (playerNames.size() == 1) {
                                        sender.sendMessage(ChatUtil.formatprefix(MaintenanceConfig.list.replace("%players%", playerNames.get(0)).replace("spelers", "speler").replace("staan", "staat")));
                                        break;
                                    } else {
                                        playerNamesMessage = playerNames.stream()
                                                .limit(playerNames.size() - 1) // Exclude the last element
                                                .collect(Collectors.joining("&a, &b")) // Join elements with comma and space delimiter
                                                + (playerNames.size() > 1 ? "&a en &b" : "") // Add " en " before the last element if there's more than one element
                                                + playerNames.get(playerNames.size() - 1); // Add the last element
                                    }
                                    sender.sendMessage(ChatUtil.formatprefix(MaintenanceConfig.list.replace("%players%", playerNamesMessage)));
                                    break;
                                }
                            }
                            sender.sendMessage(ChatUtil.formatprefix(MaintenanceConfig.listNiemand));
                            break;
                        }
                        sender.sendMessage(ChatUtil.formatprefix("&cDit command is uitgeschakeld. Bekijk console voor errors."));
                        ChatUtil.sendConsolePrefixError("De Essentials plugin is niet gevonden op de server. Installeer deze zodat de maintenance commands add, remove en list terug werken.");
                        break;
                    }
                    sender.sendMessage(ChatUtil.noPermission());
                    break;

                case "status":
                    if (sender.hasPermission("friendscraft.maintenance.status")) {
                        if (MaintenanceConfig.whitelistStatus) {
                            sender.sendMessage(ChatUtil.formatprefix(MaintenanceConfig.statusAan));
                            break;
                        }
                        if (!(MaintenanceConfig.whitelistStatus)) {
                            sender.sendMessage(ChatUtil.formatprefix(MaintenanceConfig.statusUit));
                            break;
                        }
                    }
                    sender.sendMessage(ChatUtil.noPermission());
                    break;

                case "help":
                default:
                    if (sender.hasPermission("friendscraft.maintenance.help")) {
                        sender.sendMessage(ChatUtil.formatprefix(MessageConfig.help.replace("%commands%", commandHelp)));
                        break;
                    }
                    sender.sendMessage(ChatUtil.noPermission());
                    break;
            }
        }

        if (args.length == 2) {
            switch (args[0]) {
                case "add":
                    if (sender.hasPermission("friendscraft.maintenance.add")) {
                        if (FriendsCraft.getInstance().statusEssentials()) {
                            if (args[1] != null) {
                                User player = FriendsCraft.getInstance().getEssentials().getOfflineUser(args[1]);
                                if (player != null) {
                                    if (!(MaintenanceCheck.checkMaintenance(player.getUUID()))) {
                                        List<String> whitelistString = new ArrayList(MaintenanceConfig.whitelist);
                                        whitelistString.add(player.getUUID().toString());
                                        MaintenanceConfig.save("users", whitelistString);
                                        FriendsCraft.getInstance().reloadMaintenance();
                                        sender.sendMessage(ChatUtil.formatprefix(MaintenanceConfig.addToegevoegd.replace("%player%", player.getName())));
                                        break;
                                    }
                                    if (MaintenanceCheck.checkMaintenance(player.getUUID())) {
                                        sender.sendMessage(ChatUtil.formatprefix(MaintenanceConfig.addStaatAl));
                                        break;
                                    } else {
                                        sender.sendMessage(ChatUtil.formatprefix(MaintenanceConfig.bestaatNiet));
                                        break;
                                    }
                                }
                                sender.sendMessage(ChatUtil.formatprefix(MaintenanceConfig.bestaatNiet));
                                break;
                            }
                            sender.sendMessage(ChatUtil.formatprefix(MessageConfig.onjuistCommand.replace("%commands%", commandHelp)));
                            break;
                        }
                        sender.sendMessage(ChatUtil.formatprefix("&cDit command is uitgeschakeld. Bekijk console voor errors."));
                        ChatUtil.sendConsolePrefixError("De Essentials plugin is niet gevonden op de server. Installeer deze zodat de maintenance commands add, remove en list terug werken.");
                        break;
                    }
                    sender.sendMessage(ChatUtil.noPermission());
                    break;

                case "remove":
                    if (sender.hasPermission("friendscraft.maintenance.remove")) {
                        if (FriendsCraft.getInstance().statusEssentials()) {
                            if (args[1] != null) {
                                User player = FriendsCraft.getInstance().getEssentials().getOfflineUser(args[1]);
                                if (player != null) {
                                    if (MaintenanceCheck.checkMaintenance(player.getUUID())) {
                                        List<String> whitelistString = new ArrayList(MaintenanceConfig.whitelist);
                                        whitelistString.remove(player.getUUID().toString());
                                        MaintenanceConfig.save("users", whitelistString);
                                        FriendsCraft.getInstance().reloadMaintenance();
                                        sender.sendMessage(ChatUtil.formatprefix(MaintenanceConfig.removeVerwijderd.replace("%player%", player.getName())));
                                        break;
                                    }
                                    if (!(MaintenanceCheck.checkMaintenance(player.getUUID()))) {
                                        sender.sendMessage(ChatUtil.formatprefix(MaintenanceConfig.removeStaatNiet));
                                        break;
                                    } else {
                                        sender.sendMessage(ChatUtil.formatprefix(MaintenanceConfig.bestaatNiet));
                                        break;
                                    }
                                }
                                sender.sendMessage(ChatUtil.formatprefix(MaintenanceConfig.bestaatNiet));
                                break;
                            }
                            sender.sendMessage(ChatUtil.formatprefix(MessageConfig.onjuistCommand.replace("%commands%", commandHelp)));
                            break;
                        }
                        sender.sendMessage(ChatUtil.formatprefix("&cDit command is uitgeschakeld. Bekijk console voor errors."));
                        ChatUtil.sendConsolePrefixError("De Essentials plugin is niet gevonden op de server. Installeer deze zodat de maintenance commands add, remove en list terug werken.");
                        break;
                    }
                    sender.sendMessage(ChatUtil.noPermission());
                    break;
                default:
                    if (sender.hasPermission("friendscraft.maintenance.help")) {
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
            return Arrays.asList("add", "disable", "enable", "help", "list", "remove", "status").stream().filter(p -> p.regionMatches(true, 0, args[0], 0, args[0].length())).collect(Collectors.toList());
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("add")) {
                return Bukkit.getOnlinePlayers().stream().filter(player -> player.getName().regionMatches(true,0,args[1],0,args[1].length())).map(Player::getName).collect(Collectors.toList());
            }
            if (args[0].equalsIgnoreCase("remove")) {
                if (FriendsCraft.getInstance().statusEssentials()) {
                    List<String> playerNames = new ArrayList<>();
                    for (String uuid : MaintenanceConfig.whitelist) {
                        String naam = Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName();
                        User player = FriendsCraft.getInstance().getEssentials().getOfflineUser(naam);
                        if (player != null) {
                            playerNames.add(naam);
                        }
                    }

                    return playerNames.stream()
                            .filter(playerName -> playerName.regionMatches(true, 0, args[1], 0, args[1].length()))
                            .collect(Collectors.toList());
                }
            }
        }
        return new ArrayList<>();
    }
}