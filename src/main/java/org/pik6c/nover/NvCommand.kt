package org.pik6c.nover
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.plugin.Plugin
import org.pik6c.nover.moderator.*
import org.pik6c.nover.moderator.ModeratorList


class NvCommand(private val plugin: Plugin) : CommandExecutor , TabCompleter{
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (args.isEmpty()) {
            sender.sendMessage("${ChatColor.BLACK}使用方法: /nv help")
            return true
        }

        when (args[0].lowercase()) {
            "help" -> {
                if (args.size == 2){
                    when (args[1].lowercase()){
                        "moderator" -> {
                            ModeratorHelp.moderator(sender)
                            return true
                        }
                    }
                }
                CommandHelp.nvCommandHelp(sender)
            }

            "moderator" -> {
                // オペレーターではなかった場合、実行させない
                if (!sender.isOp){
                    sender.sendMessage("${ChatColor.RED}${ChatColor.BOLD}このコマンドを実行する権限がありません！")
                    return true
                }
                if (args.size >= 2) {
                    when (args[1].lowercase()) {
                        "add" -> {
                            if (args.size == 3) {
                                Add().addModerator(args[2], sender)
                            } else {
                                ModeratorHelp.add(sender)
                            }
                        }

                        "remove" -> {
                            if (args.size == 3) {
                                Remove().removeModerator(args[2], sender, plugin)
                            } else {
                                ModeratorHelp.remove(sender)

                            }
                        }

                        "list" -> {
                            ModeratorList().listModerator(sender)
                        }

                        else -> {
                            ModeratorHelp.moderator(sender)
                        }
                    }
                } else if (args.size == 1){
                    ModeratorHelp.moderator(sender)
                }
            }

            "version" -> {
                sender.sendMessage("${ChatColor.AQUA}Nover ${ChatColor.YELLOW}v1.0${ChatColor.GRAY} PRE-RELEASE")
                sender.sendMessage("${ChatColor.DARK_AQUA}NoverはMIT Licenseでライセンスされています")

            }

            else -> {
                sender.sendMessage("${ChatColor.RED}不明な引数です: ${args[0]}")
            }
        }

        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<String>): MutableList<String> {
        val completions = mutableListOf<String>()

        when (args.size) {
            1 -> {
                completions.add("moderator")
                completions.add("user")
                completions.add("help")
            }
            2 -> {
                when(args[0]){
                    "moderator" -> {
                        completions.add("add")
                        completions.add("remove")
                        completions.add("list")
                    }
                    "user" -> {
                        val onlinePlayers = Bukkit.getOnlinePlayers()
                        for (player in onlinePlayers) {
                            if (player.name.startsWith(args[1], ignoreCase = true)) {
                                completions.add(player.name)
                            }
                        }
                    }
                }
            }

            3 -> {
                if (args[0] == "moderator" && args[1] == "add" || args[1] == "remove") {
                    // オンラインプレイヤーの補完
                    val onlinePlayers = Bukkit.getOnlinePlayers()
                    for (player in onlinePlayers) {
                        if (player.name.startsWith(args[2], ignoreCase = true)) {
                            completions.add(player.name)
                        }
                    }
                }else if (args[0] == "user"){
                    completions.add("ipban")
                    completions.add("ban")
                    completions.add("kick")
                    completions.add("kill")
                    completions.add("tp")
                }
            }
        }

        return completions
    }

}

