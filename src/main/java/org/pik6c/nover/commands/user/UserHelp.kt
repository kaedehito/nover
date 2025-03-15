package org.pik6c.nover.commands.user

import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

class UserHelp {
    companion object{
        fun TpArgNotFound(sender: CommandSender){
            sender.sendMessage("${ChatColor.RED}引数が足りません. ヘルプ：")
            sender.sendMessage("${ChatColor.YELLOW}/nv user ${ChatColor.AQUA}<player>${ChatColor.YELLOW} tp ${ChatColor.AQUA}<player>")
            return
        }

        fun KickArgNotFound(sender: CommandSender){
            sender.sendMessage("${ChatColor.RED}引数が足りません. ヘルプ：")
            sender.sendMessage("${ChatColor.YELLOW}/nv user ${ChatColor.AQUA}<player>${ChatColor.YELLOW} kick ${ChatColor.AQUA}<player>")
            return
        }
    }
}