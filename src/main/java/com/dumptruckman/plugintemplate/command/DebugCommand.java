package com.dumptruckman.plugintemplate.command;

import com.dumptruckman.plugintemplate.PluginTemplate;
import com.dumptruckman.plugintemplate.locale.Message;
import com.dumptruckman.plugintemplate.permission.Perm;
import com.dumptruckman.plugintemplate.util.Logging;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;


import java.util.List;

/**
 * Enables debug-information.
 */
public class DebugCommand extends PluginCommand {

    public DebugCommand(PluginTemplate plugin) {
        super(plugin);
        this.setName("Turn Debug on/off?");
        this.setCommandUsage("/pt debug" + ChatColor.GOLD + " [1|2|3|off]");
        this.setArgRange(0, 1);
        this.addKey("pt debug");
        this.addKey("pt d");
        this.addKey("ptdebug");
        this.addKey("ptd");
        this.addCommandExample("/pt debug " + ChatColor.GOLD + "2");
        this.setPermission(Perm.COMMAND_DEBUG.getPermission());
    }

    @Override
    public void runCommand(CommandSender sender, List<String> args) {
        if (args.size() == 1) {
            if (args.get(0).equalsIgnoreCase("off")) {
                plugin.getSettings().setGlobalDebug(0);
            } else {
                try {
                    int debugLevel = Integer.parseInt(args.get(0));
                    if (debugLevel > 3 || debugLevel < 0) {
                        throw new NumberFormatException();
                    }
                    plugin.getSettings().setGlobalDebug(debugLevel);
                } catch (NumberFormatException e) {
                    messager.bad(Message.INVALID_DEBUG, sender);
                }
            }
        }
        this.displayDebugMode(sender);
    }

    private void displayDebugMode(CommandSender sender) {
        if (plugin.getSettings().getGlobalDebug() == 0) {
            messager.normal(Message.DEBUG_SET, sender, ChatColor.RED + messager.getMessage(Message.GENERIC_OFF));
        } else {
            messager.normal(Message.DEBUG_SET, sender, ChatColor.GREEN
                    + Integer.toString(plugin.getSettings().getGlobalDebug()));
            Logging.fine("PluginTemplate Debug ENABLED");
        }
    }
}
