package com.survivaldub.pSExtraFlags.listeners;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionContainer;
import com.sk89q.worldguard.protection.managers.RegionQuery;
import com.survivaldub.pSExtraFlags.flags.CustomFlags;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TeleportListener implements Listener {
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        Location to = event.getTo();

        if (to == null) return;

        // Obtener la región usando WorldGuard y verificar la flag
        RegionContainer container = WorldGuardPlugin.inst().getRegionContainer();
        RegionQuery query = container.createQuery();
        ApplicableRegionSet set = query.getApplicableRegions(to);

        if (set.testState(player, CustomFlags.PREVENT_TELEPORT)) {
            // Verificar si el jugador es miembro de alguna región aplicable
            if (!set.getRegions().stream().anyMatch(region -> region.isMember(player.getUniqueId()))) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "¡No puedes teletransportarte a esta región!");
            }
        }
    }
}
