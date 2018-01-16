package hugu1026.com.github.phantasymagic;

import hugu1026.com.github.phantasymagic.listener.CreateMagic;
import hugu1026.com.github.phantasymagic.listener.InventoryClick;
import hugu1026.com.github.phantasymagic.listener.PlayerInteract;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class PhantasyMagic extends JavaPlugin {

    @Override
    public void onEnable() {
        super.onEnable();



        registerEvents();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new PlayerInteract(), this);
        pm.registerEvents(new InventoryClick(), this);
        pm.registerEvents(new CreateMagic(), this);
    }
}
