package org.pik6c.nover
import kotlinx.serialization.EncodeDefault
import org.bukkit.Bukkit
import org.pik6c.nover.Moderator;
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin


class NvCommand(private val plugin: Plugin) : CommandExecutor , TabCompleter{
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (args.isEmpty()) {
            sender.sendMessage("${ChatColor.RED}使用方法: /nv help")
            return true
        }

        when (args[0].lowercase()) {
            "help" -> {
                if (args.size == 2){
                    when (args[1].lowercase()){
                        "moderator" -> {
                            help.moderator(sender);
                            return true;
                        }
                    }
                }
                sender.sendMessage("${ChatColor.GREEN}======== NV コマンドヘルプ ========")
                sender.sendMessage("${ChatColor.YELLOW}/nv help ${ChatColor.WHITE}- ヘルプを表示")
                sender.sendMessage("${ChatColor.YELLOW}/nv moderator ${ChatColor.AQUA}<command>${ChatColor.WHITE} - モデレータに関する操作")
                sender.sendMessage("${ChatColor.YELLOW}/nv user ${ChatColor.AQUA}<command>${ChatColor.WHITE} - ユーザーに関する操作")
                sender.sendMessage("${ChatColor.YELLOW}/nv info ${ChatColor.WHITE}- ユーザーの情報を表示します")
            }

            "moderator" -> {
                // オペレーターではなかった場合、実行させない
                if (!sender.isOp){
                    sender.sendMessage("${ChatColor.RED}${ChatColor.BOLD}このコマンドを実行する権限がありません！");
                    return true;
                }
                if (args.size >= 2) {
                    when (args[1].lowercase()) {
                        "add" -> {
                            if (args.size == 3) {
                                Moderator.addModerator(args[2], sender)
                            } else {
                                sender.sendMessage("${ChatColor.RED}${ChatColor.BOLD}エラー: 引数が間違っています${ChatColor.RESET}");
                                sender.sendMessage("${ChatColor.GREEN}${ChatColor.BOLD}ヘルプ: ${ChatColor.RESET}");
                                sender.sendMessage("${ChatColor.YELLOW}/nv moderator add ${ChatColor.AQUA}<user>${ChatColor.WHITE} - 指定したユーザーをモデレーターに昇格させます")
                            }
                        }

                        "remove" -> {
                            if (args.size == 3) {
                                Moderator.removeModerator(args[2], sender, plugin)
                            } else {
                                sender.sendMessage("${ChatColor.RED}${ChatColor.BOLD}エラー: 引数が間違っています${ChatColor.RESET}");
                                sender.sendMessage("${ChatColor.GREEN}${ChatColor.BOLD}ヘルプ: ${ChatColor.RESET}");
                                sender.sendMessage("${ChatColor.YELLOW}/nv moderator remove ${ChatColor.AQUA}<user>${ChatColor.WHITE} - 指定したユーザーからモデレーター権限を消去します")
                            }
                        }

                        "list" -> {
                            Moderator.listsModerator(sender)
                        }

                        else -> {
                            sender.sendMessage("${ChatColor.RED}${ChatColor.BOLD}エラー: 引数が間違っています${ChatColor.RESET}");
                            sender.sendMessage("${ChatColor.GREEN}ヘルプ: ");sender.sendMessage("${ChatColor.YELLOW}/nv moderator add ${ChatColor.AQUA}<user>${ChatColor.WHITE} - 指定したユーザーをモデレーターに昇格させます")
                            sender.sendMessage("${ChatColor.YELLOW}/nv moderator remove ${ChatColor.AQUA}<user>${ChatColor.WHITE} - 指定したユーザーからモデレーター権限を消去します")
                        }
                    }
                } else if (args.size == 1){
                    sender.sendMessage("${ChatColor.RED}${ChatColor.BOLD}エラー: 引数の数が間違っています${ChatColor.RESET}");
                    sender.sendMessage("${ChatColor.GREEN}ヘルプ: ");
                    sender.sendMessage("${ChatColor.YELLOW}/nv moderator add ${ChatColor.AQUA}<user>${ChatColor.WHITE} - 指定したユーザーをモデレーターに昇格させます")
                    sender.sendMessage("${ChatColor.YELLOW}/nv moderator remove ${ChatColor.AQUA}<user>${ChatColor.WHITE} - 指定したユーザーからモデレーター権限を消去します")
                }
            }

            "version" -> {
                sender.sendMessage("${ChatColor.AQUA}Nover ${ChatColor.YELLOW}v1.0${ChatColor.GRAY} PRE-RELEASE");
                sender.sendMessage("${ChatColor.DARK_AQUA}NoverはMIT Licenseでライセンスされています");

            }

            else -> {
                sender.sendMessage("${ChatColor.RED}不明な引数です: ${args[0]}")
            }
        }

        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<String>): List<String> {
        val completions = mutableListOf<String>()

        when (args.size) {
            1 -> {
                completions.add("moderator")
                completions.add("help")
            }
            2 -> {
                if (args[0] == "moderator") {
                    completions.add("add")
                    completions.add("remove")
                    completions.add("list")
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
                }
            }
        }

        return completions
    }

}


class help {
    companion object {
        public fun moderator(sender: CommandSender) {
            sender.sendMessage("${ChatColor.GREEN}======== NV moderator コマンドヘルプ ========");
            sender.sendMessage("${ChatColor.YELLOW}/nv moderator add ${ChatColor.BLUE}<user>${ChatColor.WHITE} - 指定したユーザーをモデレータに指定します");
            sender.sendMessage("${ChatColor.YELLOW}/nv moderator remove ${ChatColor.BLUE}<user>${ChatColor.WHITE} - 指定したユーザーをモデレータから削除します")
            return;
        }

        public fun green(msg: String, sender: CommandSender) {
            sender.sendMessage(ChatColor.GREEN.toString() + msg);
        }

        public fun bold(msg: String, sender: CommandSender) {
            sender.sendMessage(ChatColor.BOLD.toString() + msg);
        }
    }
}
