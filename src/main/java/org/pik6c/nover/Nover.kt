package org.pik6c.nover
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.pik6c.nover.NvCommand

import org.bukkit.plugin.java.JavaPlugin

class Nover : JavaPlugin() {
    override fun onEnable() {
        // Plugin startup logic
        logger.info("Welcome to nover plugin (1.0-SNAPSHOT)");
        logger.info("/nv help to get started");
        getCommand("nv")?.setExecutor(NvCommand(this));
        getCommand("nv")?.tabCompleter = NvCommand(this);

    }

    override fun onDisable() {
        // Plugin shutdown logic
        logger.info("INFO: nover is disabled");
    }
}
