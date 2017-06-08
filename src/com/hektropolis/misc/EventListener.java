package com.hektropolis.misc;

import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.block.Chest;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import java.util.TimerTask;
import java.util.Timer;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;

public class EventListener implements Listener
{
    private Main plugin;
    private FileConfiguration config;

    public EventListener(final Main plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent e) {
        final String[] coords = this.config.getString("chest-location").split(",");
        final int x = Integer.parseInt(coords[0]);
        final int y = Integer.parseInt(coords[1]);
        final int z = Integer.parseInt(coords[2]);
        final Player player = e.getPlayer();
        for (final String name : this.config.getStringList("staff")) {
            if (player.getName().equalsIgnoreCase(name)) {
                final Timer t = new Timer();
                t.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        player.setGameMode(GameMode.CREATIVE);
                    }
                }, 300L);
            }
        }
        if (!player.hasPlayedBefore()) {
            final Chest chest = (Chest)player.getWorld().getBlockAt(x, y, z).getState();
            player.getInventory().addItem(new ItemStack(chest.getInventory().getItem(0) ));
        }
    }

    @EventHandler
    public void onPlayerWorldTeleport(final PlayerTeleportEvent e) {
        final Player player = e.getPlayer();
        final PopupMenuSystem menus = new PopupMenuSystem(this.plugin);
        if (!e.getFrom().getWorld().getName().equalsIgnoreCase(e.getTo().getWorld().getName()) && this.config
                .getStringList("staff").contains(player.getName())) {
            final Timer t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    player.setGameMode(GameMode.CREATIVE);
                }
            }, 300L);
        }
        if (e.getTo().getWorld().getName().equalsIgnoreCase("arcade_hall")
                && !e.getFrom().getWorld().getName().equalsIgnoreCase("arcade_hall")) {
            Bukkit.getScheduler().runTask(this.plugin, new Runnable() {
                @Override
                public void run() {
                    menus.selectGameMenu(player);
                }
            });
        }
    }

    @EventHandler
    public void getChatFormat(AsyncPlayerChatEvent e) {
        Main.config.set("chat-format", e.getFormat());
    }
}