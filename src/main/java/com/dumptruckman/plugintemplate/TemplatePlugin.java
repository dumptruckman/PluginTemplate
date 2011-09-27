package com.dumptruckman.plugintemplate;

import com.dumptruckman.plugintemplate.config.Config;
import com.dumptruckman.plugintemplate.data.Data;
import com.dumptruckman.plugintemplate.locale.Language;
import com.dumptruckman.plugintemplate.util.Logging;
import org.blockface.bukkitstats.CallHome;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author dumptruckman
 */
public class TemplatePlugin extends JavaPlugin {

    final private static Logger log = Logger.getLogger("Minecraft.PluginTemplate");

    private static TemplatePlugin instance = null;

    final public void onDisable() {
        // Save the plugin data
        Data.save(true);

        // Display disable message/version info
        Logging.info("disabled.", true);
    }

    final public void onEnable() {
        // Store the instance of this plugin
        instance = this;

        // Grab the PluginManager
        final PluginManager pm = getServer().getPluginManager();

        // Loads the configuration
        try {
            Config.load();
        } catch (IOException e) {  // Catch errors loading the config file and exit out if found.
            Logging.severe("Encountered an error while loading the configuration file.  Disabling...");
            pm.disablePlugin(this);
            return;
        }

        // Loads the language
        try {
            Language.load();
        } catch (IOException e) {  // Catch errors loading the language file and exit out if found.
            Logging.severe("Encountered an error while loading the language file.  Disabling...");
            pm.disablePlugin(this);
            return;
        }

        // Loads the data
        try {
            Data.load();
        } catch (IOException e) {  // Catch errors loading the language file and exit out if found.
            Logging.severe("Encountered an error while loading the data file.  Disabling...");
            pm.disablePlugin(this);
            return;
        }

        // Register Events
        registerEvents();

        //Call Home (usage stats)
        CallHome.load(this);

        // Display enable message/version info
        Logging.info("enabled.", true);
    }

    /**
     * Gets the logger associated with this plugin
     *
     * @return The logger associated with this plugin
     */
    final public Logger getLog() {
        return log;
    }

    final public void registerEvents() {
        final PluginManager pm = getServer().getPluginManager();
        // Event registering goes here
    }

    public static TemplatePlugin getInstance() {
        return instance;
    }
}
