package org.pik6c.nover.moderator

import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

class ModeratorHelp {

    companion object {

        fun moderator(sender: CommandSender){
            sender.sendMessage("${ChatColor.GREEN}======== NV moderator コマンドヘルプ ========")
            sender.sendMessage("${ChatColor.YELLOW}/nv moderator add ${ChatColor.BLUE}<user>${ChatColor.WHITE} - 指定したユーザーをモデレータに指定します")
            sender.sendMessage("${ChatColor.YELLOW}/nv moderator remove ${ChatColor.BLUE}<user>${ChatColor.WHITE} - 指定したユーザーをモデレータから削除します")
        }

        fun add(sender: CommandSender){
            sender.sendMessage("${ChatColor.RED}${ChatColor.BOLD}エラー: 引数が間違っています${ChatColor.RESET}")
            sender.sendMessage("${ChatColor.GREEN}${ChatColor.BOLD}ヘルプ: ${ChatColor.RESET}")
            sender.sendMessage("${ChatColor.YELLOW}/nv moderator add ${ChatColor.AQUA}<user>${ChatColor.WHITE} - 指定したユーザーをモデレーターに昇格させます")
        }

        fun remove(sender: CommandSender){
            sender.sendMessage("${ChatColor.RED}${ChatColor.BOLD}エラー: 引数が間違っています${ChatColor.RESET}")
            sender.sendMessage("${ChatColor.GREEN}${ChatColor.BOLD}ヘルプ: ${ChatColor.RESET}")
            sender.sendMessage("${ChatColor.YELLOW}/nv moderator remove ${ChatColor.AQUA}<user>${ChatColor.WHITE} - 指定したユーザーからモデレーター権限を消去します")
        }

    }


}