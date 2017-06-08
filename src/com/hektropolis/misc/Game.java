package com.hektropolis.misc;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Location;
import org.bukkit.material.MaterialData;

public enum Game
{
    WIPEOUT("WIPEOUT", 0, "Wipeout", "A 1vs1 race across a Wipeout based field", new MaterialData(Material.WATER),
            new Location(Bukkit.getWorld("arcade_hall"), -154.0, 76.0, 207.0)),
    MIND_CONTROL_MEDIUM("MIND_CONTROL_MEDIUM", 1, "Mind Control, medium level", "Get the other player to the end of the " +
            "map by controlling his moves. 2 players needed", new MaterialData(Material.LAVA),
            new Location(Bukkit.getWorld("arcade_hall"), 93.0, 88.0, 362.0)),
    MIND_CONTROL_EASY("MIND_CONTROL_EASY", 2, "Mind Control, easy level", "Get the other player to the end of the map " +
            "by controlling his moves. 2 players needed", new MaterialData(Material.LAVA),
            new Location(Bukkit.getWorld("hektor_city"), 608.0, 116.0, -737.0)),
    DOODLE_JUMP("DOODLE_JUMP", 3, "Doodle Jump 1v1", "1vs1 race to the top of the wall!",
            new MaterialData(Material.WOOL), new Location(Bukkit.getWorld("arcade_hall"),
            351.0, 134.0, -26.0)),
    ROULETTE("ROULETTE", 4, "Roulette", "A roulette wich goes from numbers 1-8. Numbers 6 and 8 have prize.",
            new MaterialData(Material.COMPASS), new Location(Bukkit.getWorld("arcade_hall"),
            -45.0, 136.0, 538.0)),
    BLACK_JACK("BLACK_JACK", 5, "Blackjack, sorta", "Add one or two to a number. The player who gets to 21 wins.",
            new MaterialData(Material.REDSTONE_COMPARATOR), new Location(Bukkit.getWorld("arcade_hall"),
            -304.0, 50.0, 347.0)),
    BOWLING("BOWLING", 6, "Bowling", "Bowling like in real life", new MaterialData(Material.SLIME_BALL),
            new Location(Bukkit.getWorld("arcade_hall"), 14.0, 114.0, 285.0));

    private final String displayName;
    private final String description;
    private final MaterialData icon;
    private final Location location;

    Game(final String s, final int n, final String displayName, final String description, final MaterialData icon, final Location location) {
        this.displayName = displayName;
        this.description = description;
        this.icon = icon;
        this.location = location;
    }

    public MaterialData getIcon() {
        return this.icon;
    }

    public String getDescription() {
        return this.description;
    }

    public Location getLocation() {
        return this.location;
    }

    @Override
    public String toString() {
        return this.displayName;
    }
}