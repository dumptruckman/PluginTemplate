package com.dumptruckman.plugintemplate;

import com.dumptruckman.plugintemplate.config.CommentedConfig;
import com.dumptruckman.plugintemplate.config.Config;
import com.dumptruckman.plugintemplate.data.Data;
import com.dumptruckman.plugintemplate.data.YamlData;
import com.dumptruckman.plugintemplate.locale.Messager;
import com.dumptruckman.plugintemplate.locale.Messaging;
import com.dumptruckman.plugintemplate.locale.SimpleMessager;
import com.dumptruckman.plugintemplate.permission.Perm;
import com.dumptruckman.plugintemplate.util.Logging;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Locale;

/**
 * @author dumptruckman
 */
public class TemplatePlugin extends JavaPlugin implements Messaging {

    private Config config = null;
    private Data data = null;
    private Messager messager = new SimpleMessager(this);

    /**
     * {@inheritDoc}
     */
    @Override
    public final void onDisable() {
        // Display disable message/version info
        Logging.info("disabled.", true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void onEnable() {
        Logging.init(this);
        Perm.register(this);

        try {
            this.getMessager().setLocale(new Locale(this.getSettings().getLocale()));
        } catch (IllegalArgumentException e) {
            Logging.severe(e.getMessage());
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        this.reloadConfig();

        // Register Events
        this.registerEvents();

        // Register Commands
        this.registerCommands();

        // Display enable message/version info
        Logging.info("enabled.", true);
    }

    /**
     * Nulls the config object and reloads a new one.
     */
    public void reloadConfig() {
        this.config = null;
        // Set debug mode from config
        Logging.setDebugMode(this.getSettings().isDebugging());
        // Create initial World Group for first run IF NO GROUPS EXIST
        if (this.getSettings().isFirstRun()) {
            Logging.info("First run!");
        }
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new PluginListener(this), this);
    }

    private void registerCommands() {
        // Command registering goes here.
    }

    /**
     * @return the MVIConfig object which contains settings for this plugin.
     */
    public Config getSettings() {
        if (this.config == null) {
            // Loads the configuration
            try {
                this.config = new CommentedConfig(this);
                Logging.debug("Loaded config file!");
            } catch (Exception e) {  // Catch errors loading the config file and exit out if found.
                Logging.severe("Error loading config file!");
                Logging.severe(e.getMessage());
                Bukkit.getPluginManager().disablePlugin(this);
                return null;
            }
        }
        return this.config;
    }

    /**
     * @return the MVIData object which contains data for this plugin.
     */
    public Data getData() {
        if (this.data == null) {
            // Loads the data
            try {
                this.data = new YamlData(this);
            } catch (IOException e) {  // Catch errors loading the language file and exit out if found.
                Logging.severe("Error loading data file!");
                Logging.severe(e.getMessage());
                Bukkit.getPluginManager().disablePlugin(this);
                return null;
            }
        }
        return this.data;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Messager getMessager() {
        return messager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMessager(Messager messager) {
        if (messager == null)
            throw new IllegalArgumentException("The new messager can't be null!");

        this.messager = messager;
    }
}
