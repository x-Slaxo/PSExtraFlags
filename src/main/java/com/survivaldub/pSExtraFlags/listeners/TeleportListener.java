package com.survivaldub.pSExtraFlags.listeners;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import com.survivaldub.pSExtraFlags.flags.CustomFlags;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TeleportListener implements Listener {

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        org.bukkit.Location bukkitLocation = event.getTo();

        if (bukkitLocation == null) return;

        // Convertir la ubicación de Bukkit a WorldEdit
        Location location = BukkitAdapter.adapt(bukkitLocation);

        // Obtener la instancia de WorldGuard y la consulta de regiones
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();

        // Obtener las regiones aplicables a la ubicación de destino
        ApplicableRegionSet regions = query.getApplicableRegions(location);

        // Convertir el jugador de Bukkit a un LocalPlayer de WorldGuard
        LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);

        // Verificar si la flag está presente y activada en alguna de las regiones
        if (regions.testState(localPlayer, CustomFlags.PREVENT_TELEPORT)) {
            for (ProtectedRegion region : regions.getRegions()) {  // Obtener las regiones individuales y recorrerlas
                if (!region.getMembers().contains(localPlayer.getUniqueId())) {
                    event.setCancelled(true);
                    player.sendMessage(ChatColor.RED + "¡No puedes teletransportarte a esta región!");
                    return;
                }
            }
        }
    }
}
