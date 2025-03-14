package org.pik6c.nover.commands

import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

class CommandHelp {

    companion object {
        fun nvCommandHelp(sender: CommandSender) {
            sender.sendMessage("${ChatColor.GREEN}======== NV コマンドヘルプ ========")
            sender.sendMessage("${ChatColor.YELLOW}/nv help ${ChatColor.WHITE}- ヘルプを表示")
            sender.sendMessage("${ChatColor.YELLOW}/nv moderator ${ChatColor.AQUA}<command>${ChatColor.WHITE} - モデレータに関する操作")
            sender.sendMessage("${ChatColor.YELLOW}/nv user ${ChatColor.AQUA}<command>${ChatColor.WHITE} - ユーザーに関する操作")
            sender.sendMessage("${ChatColor.YELLOW}/nv info ${ChatColor.WHITE}- ユーザーの情報を表示します")
        }
    }
}