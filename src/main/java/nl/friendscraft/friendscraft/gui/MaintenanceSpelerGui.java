package nl.friendscraft.friendscraft.gui;

import com.earth2me.essentials.User;
import nl.friendscraft.friendscraft.FriendsCraft;
import nl.friendscraft.friendscraft.configs.MaintenanceConfig;
import nl.friendscraft.friendscraft.utils.ChatUtil;
import nl.friendscraft.friendscraft.utils.Gui;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.UUID;

public class MaintenanceSpelerGui {

    public static void openSpelerMainGui(Player player) {
        Inventory maintenanceSpelerGui = Bukkit.createInventory(player, 9, ChatUtil.format("&6&lMaintenance &b&lSpelers &e&lMenu"));

        ItemStack addHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta addMeta = Gui.maakCustomHead(player, addHead, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWZmMzE0MzFkNjQ1ODdmZjZlZjk4YzA2NzU4MTA2ODFmOGMxM2JmOTZmNTFkOWNiMDdlZDc4NTJiMmZmZDEifX19");

        addMeta.setLore(Arrays.asList(ChatUtil.format("&7Voeg een speler toe aan de maintenance whitelist.")));
        addMeta.setDisplayName(ChatUtil.format("&aAdd &6Speler"));
        addHead.setItemMeta(addMeta);
        maintenanceSpelerGui.setItem(2, addHead);

        ItemStack listHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta listMeta = Gui.maakCustomHead(player, listHead, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTdlZDY2ZjVhNzAyMDlkODIxMTY3ZDE1NmZkYmMwY2EzYmYxMWFkNTRlZDVkODZlNzVjMjY1ZjdlNTAyOWVjMSJ9fX0=");

        listMeta.setLore(Arrays.asList(ChatUtil.format("&7Bekijk speler op de maintenance whitelist.")));
        listMeta.setDisplayName(ChatUtil.format("&9Lijst &6Spelers"));
        listHead.setItemMeta(listMeta);
        maintenanceSpelerGui.setItem(4, listHead);

        ItemStack removeHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta removeMeta = Gui.maakCustomHead(player, removeHead, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGU0YjhiOGQyMzYyYzg2NGUwNjIzMDE0ODdkOTRkMzI3MmE2YjU3MGFmYmY4MGMyYzViMTQ4Yzk1NDU3OWQ0NiJ9fX0=");

        removeMeta.setLore(Arrays.asList(ChatUtil.format("&7Verwijder een speler van de maintenance whitelist.")));
        removeMeta.setDisplayName(ChatUtil.format("&cRemove &6Speler"));
        removeHead.setItemMeta(removeMeta);
        maintenanceSpelerGui.setItem(6, removeHead);

        Gui.fillGui(maintenanceSpelerGui);

        player.openInventory(maintenanceSpelerGui);
    }

    public static void openSpelerListGui(Player player) {
        Inventory maintenanceSpelerListGui = Bukkit.createInventory(player, 36, ChatUtil.format("&6&lMaintenance &b&lList &e&lMenu"));

        ItemStack listHSpelershead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta listSpelersMeta = (SkullMeta) listHSpelershead.getItemMeta();

        for (String uuid : MaintenanceConfig.whitelist) {
            String naam = Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName();
            OfflinePlayer OfflinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
            User speler = FriendsCraft.getInstance().getEssentials().getOfflineUser(naam);
            if (speler != null) {
                listSpelersMeta.setOwningPlayer(OfflinePlayer);
                listSpelersMeta.setDisplayName(ChatUtil.format("&b" + naam));
                listSpelersMeta.setLore(Arrays.asList(ChatUtil.format("UUID: " + uuid)));
                listHSpelershead.setItemMeta(listSpelersMeta);
                maintenanceSpelerListGui.addItem(listHSpelershead);
            }
        }

        Gui.fillGui(maintenanceSpelerListGui);

        player.openInventory(maintenanceSpelerListGui);
    }

}
