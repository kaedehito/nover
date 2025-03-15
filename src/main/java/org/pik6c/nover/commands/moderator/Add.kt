package org.pik6c.nover.commands.moderator

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.pik6c.nover.utils.Ops
import java.io.File
import java.io.IOException

class Add {
    fun addModerator(user: String, sender: CommandSender) {
        val file = File("./ops.json")

        // ファイルが存在しない場合、エラーメッセージを送信
        if (!file.exists()) {
            sender.sendMessage("${ChatColor.RED}[FAILED] ${ChatColor.YELLOW}${ChatColor.BOLD}ops.jsonが見つかりません!")
            return
        }

        try {
            val fileContent = file.readText()
            val js = Json { ignoreUnknownKeys = true }
            val parsed = js.decodeFromString<MutableList<Ops>>(fileContent)


            val player = Bukkit.getPlayer(user)
            if (player == null){
                sender.sendMessage("${ChatColor.RED}[FAILED] UUIDの取得に失敗しました${ChatColor.RESET}")
                sender.sendMessage("${ChatColor.RED}ユーザー名を間違えていませんか？${ChatColor.RESET}")
                return
            }


            val uuid = player.uniqueId.toString()

            for (p in parsed){
                if (p.uuid == uuid){
                    sender.sendMessage("${ChatColor.RED}[FAILED] ユーザーはすでにops.jsonに記入されています！")
                    sender.sendMessage("${ChatColor.RED}権限レベルを変更したい場合は、ops.jsonのlevel: の欄を適切な数値に書き換えてください！")
                    return
                }
            }

            // モデレーターの追加
            parsed += Ops(
                uuid = Bukkit.getPlayer(user)?.uniqueId.toString(),
                name = user,  // ユーザー名
                level = 1,  // モデレーターのレベル
                bypassesPlayerLimit = true // プレイヤー制限を回避するか
            )

            // JSON 文字列にエンコード
            val json = Json { prettyPrint = true }
            val updatedJson = json.encodeToString(parsed)

            // ファイルに書き込む
            file.writeText(updatedJson)  // 上書き保存
            sender.sendMessage("${ChatColor.GREEN}[SUCCESS] ${ChatColor.YELLOW}モデレーターの追加が無事に完了しました")

        } catch (e: IOException) {
            sender.sendMessage("${ChatColor.RED}[FAILED] ${ChatColor.YELLOW}${ChatColor.BOLD}ops.jsonの読み込み、または書き込みに失敗しました: $e")
        }
    }
}