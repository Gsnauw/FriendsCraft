package nl.friendscraft.friendscraft.gui;

import nl.friendscraft.friendscraft.utils.ChatUtil;
import nl.friendscraft.friendscraft.utils.Gui;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

public class MaintenanceSpelerGui {

    public static void openSpelerGui(Player player) {
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

}
