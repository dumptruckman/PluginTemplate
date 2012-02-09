package com.dumptruckman.plugintemplate.command;

import com.dumptruckman.plugintemplate.PluginTemplate;
import com.dumptruckman.plugintemplate.locale.Messager;
import com.pneumaticraft.commandhandler.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * A generic Multiverse-command.
 */
public abstract class PluginCommand extends Command {

    /**
     * The reference to the core.
     */
    protected PluginTemplate plugin;
    /**
     * The reference to {@link Messager}.
     */
    protected Messager messager;

    public PluginCommand(PluginTemplate plugin) {
        super(plugin);
        this.plugin = plugin;
        this.messager = this.plugin.getMessager();
    }

    @Override
    public abstract void runCommand(CommandSender sender, List<String> args);

}
