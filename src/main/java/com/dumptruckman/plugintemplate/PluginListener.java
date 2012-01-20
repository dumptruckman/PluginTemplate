package com.dumptruckman.plugintemplate;

import org.bukkit.event.Listener;

/**
 * Class that handles all bukkit events for this Plugin.
 */
public class PluginListener implements Listener {

    private TemplatePlugin plugin;

    public PluginListener(TemplatePlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Called when a player changes worlds.
     *
     * @param event The world change event.
     */
    /*
    @EventHandler(event = PlayerChangedWorldEvent.class, priority = org.bukkit.event.EventPriority.NORMAL)
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {

    }
    */
}
