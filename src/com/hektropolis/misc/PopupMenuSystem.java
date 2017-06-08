package com.hektropolis.misc;

import org.bukkit.entity.Player;
import me.xhawk87.PopupMenuAPI.MenuItem;
import me.xhawk87.PopupMenuAPI.PopupMenuAPI;
import me.xhawk87.PopupMenuAPI.PopupMenu;

public class PopupMenuSystem
{
    private PopupMenu selectGameMenu;

    public PopupMenuSystem(final Main plugin) {
        final Game[] games = Game.values();
        this.selectGameMenu = PopupMenuAPI.createMenu("Select a game to play", (int)Math.ceil(games.length / 9.0));
        for (int i = 0; i < games.length; ++i) {
            final Game game = games[i];
            final MenuItem menuItem = new MenuItem(game.toString(), game.getIcon()) {
                public void onClick(final Player player) {
                    plugin.gameSelected(player, game);
                    this.getMenu().closeMenu(player);
                }
            };
            menuItem.setDescriptions(StringUtil.wrapWords(game.getDescription(), 40));
            this.selectGameMenu.addMenuItem(menuItem, i);
        }
    }

    public void selectGameMenu(final Player player) {
        this.selectGameMenu.openMenu(player);
    }
}