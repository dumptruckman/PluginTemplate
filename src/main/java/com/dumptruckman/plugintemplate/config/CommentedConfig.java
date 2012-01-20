package com.dumptruckman.plugintemplate.config;

import com.dumptruckman.plugintemplate.TemplatePlugin;
import com.dumptruckman.plugintemplate.config.util.CommentedYamlConfiguration;
import com.dumptruckman.plugintemplate.util.Logging;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

/**
 * Implementation of MVIConfig.
 */
public class CommentedConfig implements Config {

    /**
     * Enum for easily keeping track of config paths, defaults and comments.
     */
    public enum Path {
        /**
         * Add a comment to the top of file.
         */
        SETTINGS("settings", null, "# ===[ Multiverse Inventories Config ]==="),
        /**
         * Locale name config path, default and comments.
         */
        LANGUAGE_FILE_NAME("settings.locale", "en", "# This is the locale you wish to use."),
        /**
         * Debug Mode config path, default and comments.
         */
        DEBUG_MODE("settings.debug_mode.enable", false, "# Enables debug mode."),
        /**
         * First Run flag config path, default and comments.
         */
        FIRST_RUN("settings.first_run", true, "# If this is true it will generate world groups for you based on MV "
                + "worlds.");

        private String path;
        private Object def;
        private String[] comments;

        Path(String path, Object def, String... comments) {
            this.path = path;
            this.def = def;
            this.comments = comments;
        }

        /**
         * Retrieves the path for a config option.
         *
         * @return The path for a config option.
         */
        private String getPath() {
            return this.path;
        }

        /**
         * Retrieves the default value for a config path.
         *
         * @return The default value for a config path.
         */
        private Object getDefault() {
            return this.def;
        }

        /**
         * Retrieves the comment for a config path.
         *
         * @return The comments for a config path.
         */
        private String[] getComments() {
            if (this.comments != null) {
                return this.comments;
            }

            String[] emptyComments = new String[1];
            emptyComments[0] = "";
            return emptyComments;
        }
    }

    private CommentedYamlConfiguration config;
    private TemplatePlugin plugin;

    public CommentedConfig(TemplatePlugin plugin) throws Exception {
        this.plugin = plugin;
        // Make the data folders
        if (plugin.getDataFolder().mkdirs()) {
            Logging.debug("Created data folder.");
        }

        // Check if the config file exists.  If not, create it.
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            if (configFile.createNewFile()) {
                Logging.debug("Created config file.");
            }
        }

        // Load the configuration file into memory
        config = new CommentedYamlConfiguration(configFile);
        config.load();

        // Sets defaults config values
        this.setDefaults();

        // Saves the configuration from memory to file
        config.save();
    }

    /**
     * Loads default settings for any missing config values.
     */
    private void setDefaults() {
        for (CommentedConfig.Path path : CommentedConfig.Path.values()) {
            config.addComment(path.getPath(), path.getComments());
            if (this.getConfig().get(path.getPath()) == null) {
                if (path.getDefault() != null) {
                    Logging.debug("Config: Defaulting '" + path.getPath() + "' to " + path.getDefault());
                    this.getConfig().set(path.getPath(), path.getDefault());
                }
            }
        }
    }

    private Boolean getBoolean(Path path) {
        return this.getConfig().getBoolean(path.getPath(), (Boolean) path.getDefault());
    }

    private Integer getInt(Path path) {
        return this.getConfig().getInt(path.getPath(), (Integer) path.getDefault());
    }

    private String getString(Path path) {
        return this.getConfig().getString(path.getPath(), (String) path.getDefault());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FileConfiguration getConfig() {
        return this.config.getConfig();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save() {
        this.config.save();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDebugging() {
        return this.getBoolean(Path.DEBUG_MODE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLocale() {
        return this.getString(Path.LANGUAGE_FILE_NAME);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFirstRun() {
        return this.getBoolean(Path.FIRST_RUN);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFirstRun(boolean firstRun) {
        this.getConfig().set(Path.FIRST_RUN.getPath(), firstRun);
    }
}
