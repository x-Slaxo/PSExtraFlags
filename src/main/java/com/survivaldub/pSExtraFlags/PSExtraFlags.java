package com.survivaldub.pSExtraFlags;

import com.survivaldub.pSExtraFlags.flags.CustomFlags;
import com.survivaldub.pSExtraFlags.listeners.TeleportListener;
import org.bukkit.plugin.java.JavaPlugin;

public class PSExtraFlags extends JavaPlugin {
    @Override
    public void onEnable() {
        // Registro de flags y listeners
        CustomFlags.registerFlags();
        getServer().getPluginManager().registerEvents(new TeleportListener(), this);
        this.getLogger().info("PSExtraFlags ha sido habilitado.");
    }

    @Override
    public void onDisable() {
        getLogger().info("PSExtraFlags ha sido deshabilitado.");
    }
}