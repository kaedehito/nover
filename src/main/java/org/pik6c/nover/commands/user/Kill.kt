package org.pik6c.nover.commands.user

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

class Kill {
    fun KillPlayer(user: String, sender: CommandSender){
        val player = Bukkit.getPlayer(user)

        if(player == null){
            sender.sendMessage("${ChatColor.RED}プレイヤーが存在しません！${ChatColor.RESET}")
            return
        }

        player.sendMessage("${ChatColor.RED}モデレーターの操作によりあなたはキルされました")
        player.health = 0.0
        sender.sendMessage("${ChatColor.YELLOW}プレイヤーをキルしました")
    }
}