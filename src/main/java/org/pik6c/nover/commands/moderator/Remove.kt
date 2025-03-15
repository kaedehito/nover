package org.pik6c.nover.commands.moderator

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.pik6c.nover.utils.Ops
import java.io.File
import java.io.IOException

class Remove {
    fun removeModerator(user: String, sender: CommandSender, plugin: Plugin) {
        val file = File("./ops.json")

        // ファイルが存在しない場合、エラーメッセージを送信
        if (!file.exists()) {
            sender.sendMessage("${ChatColor.RED}[FAILED] ${ChatColor.YELLOW}${ChatColor.BOLD}ops.jsonが見つかりません!")
            return
        }

        val moderator = Bukkit.getPlayer(user)
        if (moderator == null){
            sender.sendMessage("${ChatColor.RED}[FAILED] ${ChatColor.YELLOW}${ChatColor.BOLD}指定されたユーザーが見つかりませんでした${ChatColor.RESET}")
            sender.sendMessage("${ChatColor.RED}名前を間違えていませんか？")
            return
        }

        val uuid = moderator.uniqueId.toString()


        try {
            val fileContent = file.readText()

            val json = Json { ignoreUnknownKeys = true }
            val parsed = json.decodeFromString<MutableList<Ops>>(fileContent)



            if (!parsed.any { it.uuid == uuid }){
                sender.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[FAILED] 指定されたモデレーターが見つかりませんでした${ChatColor.RESET}")
                sender.sendMessage("${ChatColor.RED}名前を間違えていませんか？")
                return
            }

            val yesNoPrompt = YesNoPrompt(plugin)
            if (sender is Player) {
                sender.player?.let {
                    yesNoPrompt.ask(it) { result -> if (result){
                        removeMode(uuid, sender, parsed, file, plugin)
                        sender.sendMessage("${ChatColor.GREEN}[SUCCESS] ${ChatColor.YELLOW}モデレーターの削除が無事に完了しました")
                    }else{
                        sender.sendMessage("${ChatColor.YELLOW}モデレーターの削除をキャンセルしました")
                    }}
                }
            }else{
                sender.sendMessage("${ChatColor.YELLOW}サーバー側から実行された操作にはY/Nプロンプトは表示されません")
                removeMode(uuid, sender, parsed, file, plugin)
            }

            return

        } catch (e: IOException) {
            sender.sendMessage("${ChatColor.RED}[FAILED] ${ChatColor.YELLOW}${ChatColor.BOLD}ops.jsonの読み込み、または書き込みに失敗しました: $e")
        }


    }

    private fun removeMode(uuid: String, sender: CommandSender, parsed: MutableList<Ops>, file: File, plugin: Plugin){
        try {
            parsed.removeIf { it.uuid == uuid }


            val json = Json { prettyPrint = true }
            val updatedJson = json.encodeToString(parsed)


            file.writeText(updatedJson)

            Bukkit.getScheduler().runTask(plugin, Runnable {
                sender.sendMessage("${ChatColor.GREEN}[SUCCESS] ${ChatColor.YELLOW}モデレーターの削除が無事に完了しました")
            })
        } catch (e: Exception) {
            println("Error in removeMode: ${e.message}")
            e.printStackTrace()

            Bukkit.getScheduler().runTask(plugin, Runnable {
                sender.sendMessage("${ChatColor.RED}[ERROR] モデレーター削除中にエラーが発生しました: ${e.message}")
            })
        }
    }
}