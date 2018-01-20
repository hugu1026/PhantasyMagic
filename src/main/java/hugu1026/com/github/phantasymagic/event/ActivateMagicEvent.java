package hugu1026.com.github.phantasymagic.event;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class ActivateMagicEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private ItemStack MAGIC_WAND;
    private Player player;
    private Location location;
    private HashMap<Integer, String> activateMagicList;

    public ActivateMagicEvent(Player player) {
        this.player = player;
        this.MAGIC_WAND = player.getInventory().getItemInMainHand();
        this.location = player.getLocation();

        List<String> lores = this.MAGIC_WAND.getItemMeta().getLore();
        for (String lore : lores) {
            lore = ChatColor.stripColor(lore);
            String[] temp = lore.split(": ", 0);
            int slot = Integer.parseInt(temp[0]);
            String magic = temp[1];

            Bukkit.getServer().broadcastMessage(slot + magic);
        }
    }

    public static  HandlerList getHandlerList() {
        return  handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public Player getPlayer() {
        return this.player;
    }

    public ItemStack getMAGIC_WAND() {
        return this.MAGIC_WAND;
    }

    public Location getLocation() {
        return this.location;
    }
}