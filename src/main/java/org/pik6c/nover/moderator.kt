package org.pik6c.nover
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Color
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.conversations.*
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException

abstract class Moderator : CommandExecutor {
    companion object {
        fun addModerator(user: String, sender: CommandSender) {
            val file = File("./ops.json")

            // ファイルが存在しない場合、エラーメッセージを送信
            if (!file.exists()) {
                sender.sendMessage("${ChatColor.RED}[FAILED] ${ChatColor.YELLOW}${ChatColor.BOLD}ops.jsonが見つかりません!")
                return
            }

            try {
                // ファイル内容を読み込む
                val fileContent = file.readText()
                println("[debug] $fileContent")

                // JSON をデコード
                val parsed = Json.decodeFromString<MutableList<Ops>>(fileContent)


                val uuid = Bukkit.getPlayer(user);
                if (uuid == null){
                    sender.sendMessage("${ChatColor.RED}[FAILED] UUIDの取得に失敗しました${ChatColor.RESET}");
                    sender.sendMessage("${ChatColor.RED}ユーザー名を間違えていませんか？${ChatColor.RESET}");
                    return;
                }

                for (p in parsed){
                    if (p.name == user){
                        sender.sendMessage("${ChatColor.RED}[FAILED] ユーザーはすでにops.jsonに記入されています！");
                        sender.sendMessage("${ChatColor.RED}権限レベルを変更したい場合は、ops.jsonのlevel: の欄を適切な数値に書き換えてください！")
                        return;
                    }
                }

                // モデレーターの追加
                parsed += Ops(
                    uuid = Bukkit.getPlayer(user)?.uniqueId.toString(), // ダミーの UUID
                    name = user,  // ユーザー名
                    level = 2,  // モデレーターのレベル
                    bypassesPlayerLimit = true // プレイヤー制限を回避するか
                )

                // JSON 文字列にエンコード
                val json = Json { prettyPrint = true }
                val updatedJson = json.encodeToString(parsed);
                println("ops.json is update to : " + updatedJson);

                // ファイルに書き込む
                file.writeText(updatedJson)  // 上書き保存
                sender.sendMessage("${ChatColor.GREEN}[SUCCESS] ${ChatColor.YELLOW}モデレーターの追加が無事に完了しました")

            } catch (e: IOException) {
                sender.sendMessage("${ChatColor.RED}[FAILED] ${ChatColor.YELLOW}${ChatColor.BOLD}ops.jsonの読み込み、または書き込みに失敗しました: $e")
            }
        }

        fun removeModerator(user: String, sender: CommandSender, plugin: Plugin) {
            val file = File("./ops.json")

            // ファイルが存在しない場合、エラーメッセージを送信
            if (!file.exists()) {
                sender.sendMessage("${ChatColor.RED}[FAILED] ${ChatColor.YELLOW}${ChatColor.BOLD}ops.jsonが見つかりません!")
                return
            }

            try {
                // ファイル内容を読み込む
                val fileContent = file.readText()
                println("[debug] $fileContent")

                // JSON をデコード
                val parsed = Json.decodeFromString<MutableList<Ops>>(fileContent)



                if (!parsed.any { it.name == user }){
                    sender.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[FAILED] 指定されたモデレーターが見つかりませんでした${ChatColor.RESET}");
                    sender.sendMessage("${ChatColor.RED}名前を間違えていませんか？");
                    return;
                }

                val yesno = YesNoPrompt(plugin);
                if (sender is Player) {
                    val asked = yesno.ask(sender as Player) {result -> if (result){
                        removeMode(user, sender, parsed, file, plugin)
                        sender.sendMessage("${ChatColor.GREEN}[SUCCESS] ${ChatColor.YELLOW}モデレーターの削除が無事に完了しました")
                    }else{
                        sender.sendMessage("${ChatColor.YELLOW}モデレーターの削除をキャンセルしました")
                    }}
                }else{
                    sender.sendMessage("${ChatColor.YELLOW}サーバー側から実行された操作にはY/Nプロンプトは表示されません");
                    removeMode(user, sender, parsed, file, plugin)
                }

                return;

            } catch (e: IOException) {
                sender.sendMessage("${ChatColor.RED}[FAILED] ${ChatColor.YELLOW}${ChatColor.BOLD}ops.jsonの読み込み、または書き込みに失敗しました: $e")
            }


        }

        fun removeMode(user: String, sender: CommandSender, parsed: MutableList<Ops>, file: File, plugin: Plugin){
            try {
                // モデレーターの削除
                parsed.removeIf { it.name == user }

                // JSON 文字列にエンコード
                val json = Json { prettyPrint = true }
                val updatedJson = json.encodeToString(parsed)
                println("ops.json is update to : " + updatedJson)

                // ファイルに書き込む
                file.writeText(updatedJson)  // 上書き保存

                // メッセージを確実にメインスレッドで送信
                Bukkit.getScheduler().runTask(plugin, Runnable {
                    sender.sendMessage("${ChatColor.GREEN}[SUCCESS] ${ChatColor.YELLOW}モデレーターの削除が無事に完了しました")
                })
            } catch (e: Exception) {
                // エラーログを出力
                println("Error in removeMode: ${e.message}")
                e.printStackTrace()

                // エラーメッセージをメインスレッドで送信
                Bukkit.getScheduler().runTask(plugin, Runnable {
                    sender.sendMessage("${ChatColor.RED}[ERROR] モデレーター削除中にエラーが発生しました: ${e.message}")
                })
            }
        }

        fun ListModerator(sender: CommandSender){
            val file = File("./ops.json").readText()

            val parsed = Json.decodeFromString<MutableList<Ops>>(file)

            for (p in parsed){
                sender.sendMessage("${ChatColor.YELLOW}${p.name}   ${p.level}")
            }



        }
    }

}

class YesNoPrompt(private val plugin: Plugin) {

    fun ask(player: Player, callback: (Boolean) -> Unit) {
        val factory = ConversationFactory(plugin)
            .withFirstPrompt(object : StringPrompt() {
                override fun getPromptText(context: ConversationContext): String {
                    return "§eはい/いいえを入力してください（yes/no）"
                }

                override fun acceptInput(context: ConversationContext, input: String?): Prompt? {
                    return when (input?.lowercase()) {
                        "yes", "y", "はい" -> {
                            callback(true)
                            null
                        }
                        "no", "n", "いいえ" -> {
                            callback(false)
                            null
                        }
                        else -> {
                            context.forWhom.sendRawMessage("§c無効な入力です。yes または no を入力してください。")
                            this
                        }
                    }
                }
            })
            .withEscapeSequence("cancel")
            .withTimeout(30)
            .thatExcludesNonPlayersWithMessage("プレイヤーのみが回答できます")

        val conversation = factory.buildConversation(player)
        player.beginConversation(conversation)
    }

}


@Serializable
data class Ops(
    val uuid: String,
    val name: String,
    val level: Int,
    val bypassesPlayerLimit: Boolean
)
