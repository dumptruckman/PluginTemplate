package com.dumptruckman.plugintemplate;

import com.dumptruckman.plugintemplate.command.DebugCommand;
import com.dumptruckman.plugintemplate.command.ReloadCommand;
import com.dumptruckman.plugintemplate.config.CommentedConfig;
import com.dumptruckman.plugintemplate.config.Config;
import com.dumptruckman.plugintemplate.data.Data;
import com.dumptruckman.plugintemplate.data.YamlData;
import com.dumptruckman.plugintemplate.locale.Messager;
import com.dumptruckman.plugintemplate.locale.Messaging;
import com.dumptruckman.plugintemplate.locale.SimpleMessager;
import com.dumptruckman.plugintemplate.permission.Perm;
import com.dumptruckman.plugintemplate.permission.PermHandler;
import com.dumptruckman.plugintemplate.util.Logging;
import com.pneumaticraft.commandhandler.CommandHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Main plugin class for dumptruckman's Plugin Template.
 */
public class PluginTemplate extends JavaPlugin implements Messaging {

    private Config config = null;
    private Data data = null;
    private Messager messager = new SimpleMessager(this);
    private File serverFolder = new File(System.getProperty("user.dir"));

    // Setup our Map for our Commands using the CommandHandler.
    private CommandHandler commandHandler;

    private PermHandler ph;

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

        this.ph = new PermHandler();
        this.commandHandler = new CommandHandler(this, this.ph);

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

        // Do any import first run stuff here.
        if (this.getSettings().isFirstRun()) {
            Logging.info("First run!");
        }
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new PluginListener(this), this);
    }

    private void registerCommands() {
        this.commandHandler.registerCommand(new DebugCommand(this));
        this.commandHandler.registerCommand(new ReloadCommand(this));
    }

    /**
     * @return The instance of CommandHandler used by this plugin.
     */
    public CommandHandler getCommandHandler() {
        return this.commandHandler;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (!this.isEnabled()) {
            sender.sendMessage("This plugin is Disabled!");
            return true;
        }
        List<String> allArgs = new ArrayList<String>(Arrays.asList(args));
        allArgs.add(0, command.getName());
        return this.getCommandHandler().locateAndRunCommand(sender, allArgs);
    }

    /**
     * @return the Config object which contains settings for this plugin.
     */
    public Config getSettings() {
        if (this.config == null) {
            // Loads the configuration
            try {
                this.config = new CommentedConfig(this);
                Logging.fine("Loaded config file!");
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
     * @return the Data object which contains data for this plugin.
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

    /**
     * Gets the server's root-folder as {@link File}.
     *
     * @return The server's root-folder
     */
    public File getServerFolder() {
        return serverFolder;
    }

    /**
     * Sets this server's root-folder.
     *
     * @param newServerFolder The new server-root
     */
    public void setServerFolder(File newServerFolder) {
        if (!newServerFolder.isDirectory())
            throw new IllegalArgumentException("That's not a folder!");

        this.serverFolder = newServerFolder;
    }
}
