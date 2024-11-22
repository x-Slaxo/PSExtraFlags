package com.survivaldub.pSExtraFlags.flags;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;

public class CustomFlags {
    public static StateFlag PREVENT_TELEPORT;

    public static void registerFlags() {
        FlagRegistry registry = WorldGuardPlugin.inst().getFlagRegistry();
        try {
            PREVENT_TELEPORT = new StateFlag("prevent-teleport", false);
            registry.register(PREVENT_TELEPORT);
        } catch (FlagConflictException e) {
            Flag<?> existing = registry.get("prevent-teleport");
            if (existing instanceof StateFlag) {
                PREVENT_TELEPORT = (StateFlag) existing;
            } else {
                throw new RuntimeException("Error registrando la flag prevent-teleport", e);
            }
        }
    }

}
