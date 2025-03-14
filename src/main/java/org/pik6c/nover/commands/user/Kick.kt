package org.pik6c.nover.commands.user

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

class Kick {
    fun KickPlayer(user: String, reason: String, sender: CommandSender){
        val player = Bukkit.getPlayer(user)

        if(player == null){
            sender.sendMessage("${ChatColor.RED}プレイヤーが見つかりません");
            return
        }

        player.kickPlayer(reason)
        Bukkit.getLogger().warning("$user をキックしました 理由：$reason")
        sender.sendMessage("$user をKickしました 理由: $reason");
    }
}