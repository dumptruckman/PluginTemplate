package com.dumptruckman.plugintemplate.config;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * Interface for interacting with the config of this plugin.
 */
public interface Config {

    /**
     * Retrieves the underlying FileConfiguration object for direct modification.
     *
     * @return The underlying FileConfiguration object.
     */
    FileConfiguration getConfig();

    /**
     * Convenience method for saving the config to disk.
     */
    void save();

    /**
     * Sets globalDebug.
     * @param globalDebug The new value.
     */
    void setGlobalDebug(int globalDebug);

    /**
     * Gets globalDebug.
     * @return globalDebug.
     */
    int getGlobalDebug();

    /**
     * Retrieves the locale string from the config.
     *
     * @return The locale string.
     */
    String getLocale();

    /**
     * Tells whether this is the first time the plugin has run as set by a config flag.
     *
     * @return True if first_run is set to true in config.
     */
    boolean isFirstRun();

    /**
     * Sets the first_run flag in the config so that the plugin no longer thinks it is the first run.
     *
     * @param firstRun What to set the flag to in the config.
     */
    void setFirstRun(boolean firstRun);
}
