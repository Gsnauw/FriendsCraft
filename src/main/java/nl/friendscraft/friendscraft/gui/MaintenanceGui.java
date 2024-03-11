package nl.friendscraft.friendscraft.gui;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import nl.friendscraft.friendscraft.utils.ChatUtil;
import nl.friendscraft.friendscraft.utils.Gui;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.UUID;

public class MaintenanceGui {

    public static void openGui(Player player) {
        Inventory maintenanceMainGui = Bukkit.createInventory(player, 27, ChatUtil.format("&6&lMaintenance &e&lMenu"));

        ItemStack spelerHead = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta spelerMeta = spelerHead.getItemMeta();

        spelerHead.setLore(Arrays.asList(ChatUtil.format("&7Voeg een speler toe aan de maintenance whitelist.")));
        spelerHead.setDisplayName(ChatUtil.format("&6Spelers"));
        spelerHead.setItemMeta(spelerHead);

        maintenanceMainGui.setItem(2, addHead);

        ItemStack listHead = new ItemStack(Material.ANVIL);
        SkullMeta listMeta = Gui.maakCustomHead(player, listHead, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTdlZDY2ZjVhNzAyMDlkODIxMTY3ZDE1NmZkYmMwY2EzYmYxMWFkNTRlZDVkODZlNzVjMjY1ZjdlNTAyOWVjMSJ9fX0=");

        listMeta.setLore(Arrays.asList(ChatUtil.format("&7Klik hier om de status te wijzigen")));
        listMeta.setDisplayName(ChatUtil.format("&6Status"));
        listHead.setItemMeta(listMeta);

        maintenanceMainGui.setItem(4, listHead);

        ItemStack removeHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta removeMeta = Gui.maakCustomHead(player, removeHead, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGU0YjhiOGQyMzYyYzg2NGUwNjIzMDE0ODdkOTRkMzI3MmE2YjU3MGFmYmY4MGMyYzViMTQ4Yzk1NDU3OWQ0NiJ9fX0=");

        removeMeta.setLore(Arrays.asList(ChatUtil.format("&7Verwijder een speler van de maintenance whitelist.")));
        removeMeta.setDisplayName(ChatUtil.format("&cRemove &6Speler"));
        removeHead.setItemMeta(removeMeta);

        maintenanceMainGui.setItem(6, removeHead);


        Gui.fillGui(maintenanceMainGui);
        player.openInventory(maintenanceMainGui);
    }
}