package com.dumptruckman.plugintemplate.command;

import com.dumptruckman.plugintemplate.PluginTemplate;
import com.dumptruckman.plugintemplate.locale.Message;
import com.dumptruckman.plugintemplate.permission.Perm;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Enables debug-information.
 */
public class ReloadCommand extends PluginCommand {

    public ReloadCommand(PluginTemplate plugin) {
        super(plugin);
        this.setName("Reloads config file");
        this.setCommandUsage("/pt reload");
        this.setArgRange(0, 0);
        this.addKey("pt reload");
        this.addKey("ptreload");
        this.setPermission(Perm.COMMAND_RELOAD.getPermission());
    }

    @Override
    public void runCommand(CommandSender sender, List<String> args) {
        plugin.reloadConfig();
        messager.normal(Message.RELOAD_COMPLETE, sender);
    }
}
