package com.dumptruckman.plugintemplate.permission;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Permission Enum for easily keeping track of permissions related to this plugin.
 */
public enum Perm {
    /**
     * Permission for debug command.
     */
    COMMAND_DEBUG(new Permission("plugintemplate.debug", "Spams the console a bunch.", PermissionDefault.OP)),
    /**
     * Permission for reload command.
     */
    COMMAND_RELOAD(new Permission("plugintemplate.reload", "Reloads the config file.", PermissionDefault.OP));

    private Permission perm = null;
    private String permNode = "";

    Perm(Permission perm) {
        this.perm = perm;
    }

    Perm(String permNode) {
        this.permNode = permNode;
    }

    /**
     * @return the Permission.
     */
    public Permission getPermission() {
        return this.perm;
    }

    /**
     * @return the Permission node string.
     */
    public String getNode() {
        return this.permNode;
    }

    /**
     * Checks if the sender has the node in question.
     *
     * @param sender CommandSender to check permission for.
     * @return True if sender has the permission.
     */
    public boolean has(CommandSender sender) {
        return sender.hasPermission(perm);
    }

    /**
     * Registers all Permission to the plugin.
     *
     * @param plugin Plugin to register permissions to.
     */
    public static void register(JavaPlugin plugin) {
        PluginManager pm = plugin.getServer().getPluginManager();
        for (Perm perm : Perm.values()) {
            if (perm.getPermission() != null) {
                pm.addPermission(perm.getPermission());
            }
        }
    }
}

