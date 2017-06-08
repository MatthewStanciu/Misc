package com.hektropolis.misc;

import org.bukkit.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.bukkit.material.MaterialData;
import org.bukkit.inventory.Recipe;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public final Logger log;
    public static FileConfiguration config;
    String[] defaultRecipes;
    String[] defaultPlayers;
    String[] defaultStaff;

    public Main() {
        this.log = Logger.getLogger("Minecraft");
        this.defaultRecipes = new String[]{"58", "46"};
        this.defaultPlayers = new String[]{"derpface", "facederp"};
        this.defaultStaff = new String[]{"solivero", "hektoor", "Didgitalpunk", "AdvancedCrono", "LucaCatrina", "Alecfrances01", "Yourgreatlord", "Jortyhank", "stephen1998x"};
    }

    public void onEnable() {
        Main.config = this.getConfig();
        this.setupConfig();
        this.getServer().getPluginManager().registerEvents(new EventListener(this), this);
        final List<Integer> itemIds = Main.config.getIntegerList("disable-item-id-crafting");
        for (final int itemId : itemIds) {
            final Iterator<Recipe> it = this.getServer().recipeIterator();
            while (it.hasNext()) {
                if (it.next().getResult().getTypeId() == itemId) {
                    it.remove();
                    this.log.info("Removed recipe for " + new MaterialData(itemId).getItemType().toString()
                            .toLowerCase().replace("_", " "));
                }
            }
        }
    }

    public void setupConfig() {
        Main.config.addDefault("disable-item-id-crafting", Arrays.asList(this.defaultRecipes));
        Main.config.addDefault("minigame-resets.zombieslaughter.coords", "1,1,1");
        Main.config.addDefault("chest-location", "0,0,0");
        Main.config.addDefault("staff", Arrays.asList(this.defaultStaff));
        Main.config.addDefault("players-on-1000-day", Arrays.asList(this.defaultPlayers));
        Main.config.addDefault("give-on-1000-day", true);
        Main.config.options().copyDefaults(true);
        this.saveConfig();
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("miscreload")) {
            this.reloadConfig();
            this.saveConfig();
        } else if (cmd.getName().equalsIgnoreCase("reset")) {
            if (args.length == 1) {
                final String coord = Main.config.getString("minigame-resets." + args[0] + ".coords");
                final String[] splitCoord = coord.split(",");
                final World w = this.getServer().getWorlds().get(0);
                final Location loc = new Location(w, (double) Integer.parseInt(splitCoord[0]),
                        (double) Integer.parseInt(splitCoord[1]), (double) Integer.parseInt(splitCoord[2]));
                final Material original = w.getBlockAt(loc).getType();
                sender.sendMessage("Reseting " + original.toString().toLowerCase().replace("_", " ") + " at x:"
                        + splitCoord[0] + " y:" + splitCoord[1] + " z:" + splitCoord[2] + " for " + args[0]);
                w.getBlockAt(loc).setType(Material.AIR);
                Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                    @Override
                    public void run() {
                        w.getBlockAt(loc).setType(original);
                    }
                }, 50L);
                return true;
            }
        } else if (cmd.getName().equalsIgnoreCase("gameslist")) {
            for (final String thang : Main.config.getConfigurationSection("minigame-resets").getKeys(false)) {
                sender.sendMessage(thang);
            }
        } else {
            if (cmd.getName().equalsIgnoreCase("infobook")) {
                Player player = null;
                if (args.length == 1) {
                    player = this.getServer().getPlayer(args[0]);
                }
                if (args.length == 0 && sender instanceof Player) {
                    player = (Player) sender;
                }
                if (player != null) {
                    final String[] coords = this.getConfig().getString("chest-location").split(",");
                    final int x = Integer.parseInt(coords[0]);
                    final int y = Integer.parseInt(coords[1]);
                    final int z = Integer.parseInt(coords[2]);
                    final Chest chest = (Chest) player.getWorld().getBlockAt(x, y, z).getState();
                    player.getInventory().addItem(new ItemStack(chest.getInventory().getItem(0)));
                }
                return true;
            }
            if (cmd.getName().equalsIgnoreCase("gamemenu")) {
                final PopupMenuSystem menus = new PopupMenuSystem(this);
                Bukkit.getScheduler().runTask(this, new Runnable() {
                    @Override
                    public void run() {
                        menus.selectGameMenu((Player) sender);
                    }
                });
            } else if (cmd.getName().equalsIgnoreCase("arcade")) {
                final Player player = (Player) sender;
                player.teleport(new Location(Bukkit.getWorld("arcade_hall"), -265.0, 74.0, 219.0));
                final PopupMenuSystem menus2 = new PopupMenuSystem(this);
                Bukkit.getScheduler().runTask(this, new Runnable() {
                    @Override
                    public void run() {
                        menus2.selectGameMenu(player);
                    }
                });
            } else if (cmd.getName().equalsIgnoreCase("city")) {
                final Player player = (Player) sender;
                player.teleport(Bukkit.getWorld("hektor_city").getSpawnLocation());
            }
        }
        return true;
    }

    public void gameSelected(final Player player, final Game game) {
        player.teleport(game.getLocation());
    }
}