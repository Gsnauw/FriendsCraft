package nl.friendscraft.friendscraft.events;

import nl.friendscraft.friendscraft.utils.Debug;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class DoubleShulkerShells  implements Listener {
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        if (entity.getType() == EntityType.SHULKER) {
            event.getDrops().clear();
            ItemStack shulkerStack = new ItemStack(Material.SHULKER_SHELL, 2);
            event.getEntity().getWorld().dropItemNaturally(entity.getLocation(), shulkerStack);
            Debug.format("Shulker shells gedropt.");
            //op locatie aan debug toevoegen
        }

    }

}
