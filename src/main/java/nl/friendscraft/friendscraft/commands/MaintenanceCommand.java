package nl.friendscraft.friendscraft.commands;

import com.earth2me.essentials.User;
import nl.friendscraft.friendscraft.FriendsCraft;
import nl.friendscraft.friendscraft.checks.MaintenanceCheck;
import nl.friendscraft.friendscraft.configs.MaintenanceConfig;
import nl.friendscraft.friendscraft.events.MaintenanceKick;
import nl.friendscraft.friendscraft.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
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

    String commandHelp = "/fcm <enable, disable, help, status, list, add (naam), remove (naam)>";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length <= 0) {
            sender.sendMessage(ChatUtil.formatprefix("&cOnjuist commando, gebruik: " + commandHelp));
            return false;
        }

        if (args.length == 1) {
            switch (args[0]) {
                case "enable":
                    if (sender.hasPermission("friendscraft.maintenance.enable")) {
                        if (MaintenanceConfig.whitelistStatus) {
                            MaintenanceConfig.save("whitelist", true);
                            FriendsCraft.getInstance().reloadMaintenance();
                            MaintenanceKick.enable();
                            sender.sendMessage(ChatUtil.formatprefix("&9De maintenance mode is &aingeschakeld."));
                            break;
                        }
                        if (!(MaintenanceConfig.whitelistStatus)) {
                            sender.sendMessage(ChatUtil.formatprefix("&cDe maintenance mode is al ingeschakeld."));
                            break;
                        }
                    }
                    sender.sendMessage(ChatUtil.noPermission());
                case "help":
                    if (sender.hasPermission("friendscraft.help")) {
                        sender.sendMessage(ChatUtil.formatprefix("&9Gebruik: " + commandHelp));
                        break;
                    }
                    sender.sendMessage(ChatUtil.noPermission());
                case "list":
                    if (sender.hasPermission("friendscraft.maintenance.list")) {

                        if (MaintenanceConfig.whitelist != null && !MaintenanceConfig.whitelist.isEmpty()) {
                            List<UUID> whitelistUUID = MaintenanceConfig.whitelist.stream()
                                    .map(u -> UUID.fromString(u))
                                    .collect(Collectors.toList());

                            List<String> playerNames = new ArrayList<>();

                            for (UUID uuid : whitelistUUID) {
                                String playerName = Bukkit.getOfflinePlayer(uuid).getName();
                                if (playerName != null) {
                                    playerNames.add(playerName);
                                }
                            }
                            String playerNamesMessage = playerNames.stream()
                                    .limit(playerNames.size() - 1) // Exclude the last element
                                    .collect(Collectors.joining("&a, &b")) // Join elements with comma and space delimiter
                                    + (playerNames.size() > 1 ? "&a en &b" : "") // Add " en " before the last element if there's more than one element
                                    + playerNames.get(playerNames.size() - 1); // Add the last element

                            sender.sendMessage(ChatUtil.formatprefix("&9Deze spelers staan op de maintenance whitelist: &b" + playerNamesMessage + "&9."));

                        } else {
                            sender.sendMessage(ChatUtil.formatprefix("&cEr staat niemand op de whitelist momenteel of de persoon(en) op de whitelist is nog nooit eerder gejoined."));
                        }
                        break;
                    }
                    sender.sendMessage(ChatUtil.noPermission());

                case "disable":
                    if (sender.hasPermission("friendscraft.maintenance.disable")) {
                        if (!(MaintenanceConfig.whitelistStatus)) {
                            sender.sendMessage(ChatUtil.formatprefix("&cDe maintenance mode is al uitgeschakeld."));
                            break;
                        }
                        if (MaintenanceConfig.whitelistStatus) {
                            MaintenanceConfig.save("whitelist", false);
                            FriendsCraft.getInstance().reloadMaintenance();
                            sender.sendMessage(ChatUtil.formatprefix("&9De maintenance mode is &cuitgeschakeld."));
                            break;
                        }
                    }
                    sender.sendMessage(ChatUtil.noPermission());
                case "status":
                    if (sender.hasPermission("friendscraft.maintenance.status")) {
                        if (MaintenanceConfig.whitelistStatus) {
                            sender.sendMessage(ChatUtil.formatprefix("&9De maintenance mode is momenteel &aingeschakeld."));
                            break;
                        }
                        if (!(MaintenanceConfig.whitelistStatus)) {
                            sender.sendMessage(ChatUtil.formatprefix("&9De maintenance mode is momenteel &cuitgeschakeld."));
                            break;
                        }
                    }
                    sender.sendMessage(ChatUtil.noPermission());
                default:
                    if (sender.hasPermission("friendscraft.help")) {
                        sender.sendMessage(ChatUtil.formatprefix("&cOnjuist commando, gebruik: " + commandHelp));
                        break;
                    }
                    sender.sendMessage(ChatUtil.noPermission());
            }
        }
        if (args.length == 2) {
            switch (args[0]) {
                case "add":
                    if (sender.hasPermission("friendscraft.maintenance.add")) {

                        //String player = Bukkit.getOfflinePlayer(args[1]).getName();
                        //  OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]); //we halen eerst het complete offline object op

                        User offlinePlayer = FriendsCraft.getInstance().getEssentials().getOfflineUser(args[1]);

                        if (offlinePlayer != null) { //controleren of hij wel aanwezig is

                            if (!(MaintenanceCheck.checkMaintenance(offlinePlayer.getUUID()))) { //offlineplayer
                                // UUID playerUUID = Bukkit.getOfflinePlayer(args[1]).getUniqueId(); //niet nodig, we hebben al het offlineplayer object en kunnen dus de UUID en naam ophalen

                                //we gaan het makkelijk houden :P
                                List<String> tmplist = new ArrayList(MaintenanceConfig.whitelist); // we maken een copy van de bestaande lijst
                                tmplist.add(offlinePlayer.getUUID().toString()); //toevoegen van de UUID als string

                                 /*
                                List<UUID> whitelistUUID = MaintenanceConfig.whitelist.stream()
                                        .map(u -> UUID.fromString(u))
                                        .collect(Collectors.toList());
                                whitelistUUID.add(offlinePlayer.getUniqueId());

                                List<String> whitelistString = whitelistUUID.stream()
                                        .map(u -> u.toString())
                                        .collect(Collectors.toList());
                                 */

                                MaintenanceConfig.save("users", tmplist);
                                FriendsCraft.getInstance().reloadMaintenance();
                                sender.sendMessage(ChatUtil.formatprefix("&9Speler met naam &b" + offlinePlayer.getName() + "&9 werd &atoegevoegd &9aan de maintenance whitelist."));
                                break;
                            }
                            if (MaintenanceCheck.checkMaintenance(offlinePlayer.getUUID())) {
                                sender.sendMessage(ChatUtil.formatprefix("&cDeze speler staat al op de whitelist momenteel."));
                                break;
                            }
                        } else {
                            sender.sendMessage(ChatUtil.formatprefix("&cDeze speler bestaat niet of is nog nooit online geweest."));
                            break;
                        }
                    }
                    break;

                case "remove":
                    if (sender.hasPermission("friendscraft.maintenance.remove")) {
                        String player = Bukkit.getOfflinePlayer(args[1]).getName();

                        if (player != null) {
                            UUID playerUUID = Bukkit.getOfflinePlayer(args[1]).getUniqueId();

                            if (MaintenanceCheck.checkMaintenance(playerUUID)) {
                                List<UUID> whitelistUUID = MaintenanceConfig.whitelist.stream()
                                        .map(u -> UUID.fromString(u))
                                        .collect(Collectors.toList());

                                whitelistUUID.remove(playerUUID);

                                List<String> whitelistString = whitelistUUID.stream()
                                        .map(u -> u.toString())
                                        .collect(Collectors.toList());

                                MaintenanceConfig.save("users", whitelistString);
                                FriendsCraft.getInstance().reloadMaintenance();
                                sender.sendMessage(ChatUtil.formatprefix("&9Speler met naam &b" + player + "&9 werd &cverwijderd &9van de maintenance whitelist."));
                                break;
                            }

                            if (!(MaintenanceCheck.checkMaintenance(playerUUID))) {
                                sender.sendMessage(ChatUtil.formatprefix("&cDeze speler staat niet op de whitelist momenteel."));
                                break;
                            }
                        } else {
                            sender.sendMessage(ChatUtil.formatprefix("&cDeze speler bestaat niet of is nog nooit online geweest."));
                            break;
                        }
                    }
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
                List<UUID> whitelistUUID = MaintenanceConfig.whitelist.stream()
                        .map(u -> UUID.fromString(u))
                        .collect(Collectors.toList());

                List<String> whitelistStringName = new ArrayList<>();

                for (UUID uuid : whitelistUUID) {
                    String playerName = Bukkit.getOfflinePlayer(uuid).getName();
                    if (playerName != null) {
                        whitelistStringName.add(playerName);
                    }
                }


                return whitelistStringName.stream()
                        .filter(playerName -> playerName.regionMatches(true, 0, args[1], 0, args[1].length()))
                        .collect(Collectors.toList());
            }
        }
        return new ArrayList<>();
    }
}