package nl.friendscraft.friendscraft.gui;

import nl.friendscraft.friendscraft.utils.ChatUtil;
import nl.friendscraft.friendscraft.utils.Gui;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Arrays;

public class MaintenanceGui {

    public static void openGui(Player player) {
        Inventory maintenanceMainGui = Bukkit.createInventory(player, 36, ChatUtil.format("&6&lMaintenance &e&lMenu"));

        ItemStack spelerHead = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta spelerMeta = spelerHead.getItemMeta();

        spelerMeta.setLore(Arrays.asList(ChatUtil.format("&7Het speler menu.")));
        spelerMeta.setLore(Arrays.asList(ChatUtil.format("&6Bekijk of wijzig de spelers op de whitelist.")
                , ChatUtil.format("&6&l(&e&lKlik!&6&l)")));
        spelerMeta.setDisplayName(ChatUtil.format("&9-> &6Spelers &9<-"));
        spelerHead.setItemMeta(spelerMeta);

        maintenanceMainGui.setItem(16, spelerHead);


        ItemStack statusItem = new ItemStack(Material.ANVIL);
        ItemMeta statusMeta = statusItem.getItemMeta();

        statusMeta.setLore(Arrays.asList(ChatUtil.format("&7Klik hier om de status te wijzigen")
                , ChatUtil.format("&6&l(&e&lKlik!&6&l)")));
        statusMeta.setDisplayName(ChatUtil.format("&9-> &6Status &9<-"));
        statusItem.setItemMeta(statusMeta);

        maintenanceMainGui.setItem(10, statusItem);

        ItemStack bookItem = new ItemStack(Material.BOOK);
        ItemMeta bookMeta = bookItem.getItemMeta();

        bookMeta.setLore(Arrays.asList(ChatUtil.format("&eDit is het maintenance menu.")
                , ChatUtil.format("&eBepaal hier de whitelisted spelers")
                , ChatUtil.format("&eof verander de maintenance status.")));
        bookMeta.setDisplayName(ChatUtil.format("&9-> &6Info &9<-"));
        bookItem.setItemMeta(bookMeta);

        maintenanceMainGui.setItem(13, bookItem);

        ItemStack barrierItem = new ItemStack(Material.BARRIER);
        ItemMeta barrierMeta = barrierItem.getItemMeta();

        barrierMeta.setLore(Arrays.asList(ChatUtil.format("&6Sluit dit menu.")
                , ChatUtil.format("&6&l(&e&lKlik!&6&l)")));
        barrierMeta.setDisplayName(ChatUtil.format("&9-> &cSluit &9<-"));
        barrierItem.setItemMeta(barrierMeta);

        maintenanceMainGui.setItem(31, barrierItem);


        Gui.fillGui(maintenanceMainGui);
        player.openInventory(maintenanceMainGui);
    }
}