package org.pik6c.nover

import org.bukkit.plugin.java.JavaPlugin

class Nover : JavaPlugin() {
    override fun onEnable() {
        // Plugin startup logic
        logger.info("Welcome to nover plugin (1.0-SNAPSHOT)")
        logger.info("/nv help to get started")
        getCommand("nv")?.setExecutor(NvCommand(this))
        getCommand("nv")?.tabCompleter = NvCommand(this)
        getCommand("nv")?.aliases = mutableListOf("nover")

        server.pluginManager.registerEvents(JoinMessage(), this)
        server.pluginManager.registerEvents(QuitMessage(), this)
        server.pluginManager.registerEvents(ChatFilter(), this)
        server.pluginManager.registerEvents(ChatColor(), this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
        logger.info("INFO: nover is disabled")
    }
}
