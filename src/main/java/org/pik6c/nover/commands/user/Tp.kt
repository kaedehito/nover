package org.pik6c.nover.commands.user

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

class Tp {

    fun TpPlayer(from: String, to: String,sender: CommandSender){
        val fromPlayer = Bukkit.getPlayer(from)
        val toPlayer = Bukkit.getPlayer(to)

        if (fromPlayer == null){
            sender.sendMessage("${ChatColor.RED}プレイヤーが見つかりません：$from")
            return
        }

        if (toPlayer == null){
            sender.sendMessage("${ChatColor.RED}プレイヤーが見つかりません：$to")
            return
        }


        val toPlayerLocation = toPlayer.location
        fromPlayer.teleport(toPlayerLocation)
        fromPlayer.sendMessage("${ChatColor.AQUA}${toPlayerLocation.toString()}${ChatColor.RESET}へワープしました");
        toPlayer.sendMessage("${ChatColor.AQUA}${fromPlayer.name}${ChatColor.RESET}があなたにテレポートしました");

    }
}