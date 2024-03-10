package nl.friendscraft.friendscraft.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
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
        Inventory maintenanceGui = Bukkit.createInventory(player, 9, ChatUtil.format("&6&lMaintenance &b&lSpelers &e&lMenu"));

        ItemStack addHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta addMeta = maakCustomHead(player, addHead, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWZmMzE0MzFkNjQ1ODdmZjZlZjk4YzA2NzU4MTA2ODFmOGMxM2JmOTZmNTFkOWNiMDdlZDc4NTJiMmZmZDEifX19");

        addMeta.setLore(Arrays.asList(ChatUtil.format("&7Voeg een speler toe aan de maintenance whitelist.")));
        addMeta.setDisplayName(ChatUtil.format("&aAdd &6Speler"));
        addHead.setItemMeta(addMeta);

        maintenanceGui.setItem(2, addHead);

        ItemStack listHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta listMeta = maakCustomHead(player, listHead, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTdlZDY2ZjVhNzAyMDlkODIxMTY3ZDE1NmZkYmMwY2EzYmYxMWFkNTRlZDVkODZlNzVjMjY1ZjdlNTAyOWVjMSJ9fX0=");

        listMeta.setLore(Arrays.asList(ChatUtil.format("&7Bekijk speler op de maintenance whitelist.")));
        listMeta.setDisplayName(ChatUtil.format("&9Lijst &6Spelers"));
        listHead.setItemMeta(listMeta);

        maintenanceGui.setItem(4, listHead);

        ItemStack removeHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta removeMeta = maakCustomHead(player, removeHead, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGU0YjhiOGQyMzYyYzg2NGUwNjIzMDE0ODdkOTRkMzI3MmE2YjU3MGFmYmY4MGMyYzViMTQ4Yzk1NDU3OWQ0NiJ9fX0=");

        removeMeta.setLore(Arrays.asList(ChatUtil.format("&7Verwijder een speler van de maintenance whitelist.")));
        removeMeta.setDisplayName(ChatUtil.format("&cRemove &6Speler"));
        removeHead.setItemMeta(removeMeta);

        maintenanceGui.setItem(6, removeHead);

        fillGui(maintenanceGui);

        /*
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta metaHead = (SkullMeta) head.getItemMeta();
        metaHead.setOwningPlayer(player);
        metaHead.setLore(Arrays.asList(ChatUtil.format("&7Voeg een speler toe aan de maintenance whitelist.")));
        metaHead.setDisplayName(ChatUtil.format("&6Enge head van Koe"));
        head.setItemMeta(metaHead);

        maintenanceGui.setItem(4, head);
        */


        player.openInventory(maintenanceGui);
    }

    public static SkullMeta maakCustomHead(Player player, ItemStack head, String value) {
        SkullMeta meta = (SkullMeta) head.getItemMeta();

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", value));
        Field field;
        try{
            field = meta.getClass().getDeclaredField("profile");
            field.setAccessible(true);
            field.set(meta, profile);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return meta;
    }

    public static void fillGui(Inventory inventory) {
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null || inventory.getItem(i).getType() == Material.AIR) {
                ItemStack item = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
                ItemMeta itemMeta = item.getItemMeta();
                itemMeta.setDisplayName("");
                inventory.setItem(i, item);
            }
        }
    }
}