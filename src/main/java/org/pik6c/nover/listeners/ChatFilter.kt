package org.pik6c.nover.listeners

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import java.nio.file.Files
import java.nio.file.Path

class ChatFilter : Listener {

    private val parsed: FilterJson by lazy {
        val filePath = Path.of("./nover/filter.json")
        val dirPath = filePath.parent

        Files.createDirectories(dirPath)

        val file = filePath.toFile()
        if (!file.exists()) {
            {}.javaClass.classLoader.getResourceAsStream("filter.json")?.use { input ->
                file.writeText(input.bufferedReader().readText())
            }
        }

        val json = Json { ignoreUnknownKeys = true }
        val parsed = json.decodeFromString<FilterJson>(file.readText())

        // ログを出力（デバッグ用）
        Bukkit.getLogger().info("[nover] フィルターを読み込みました：${parsed.filterMessages}")

        parsed
    }

    @EventHandler
    fun onPlayerChat(event: AsyncPlayerChatEvent) {
        val player = event.player
        var message = event.message

        var found = false


        for (pattern in parsed.filterMessages) {
            val regex = pattern.toRegex()
            if (regex.containsMatchIn(message)) {
                found = true
                player.sendMessage("${ChatColor.RED}あなたのメッセージはフィルターされました！")
                message = regex.replace(message) { matchResult -> "${ChatColor.RED}${matchResult.value}${ChatColor.RESET}" }
            }
        }

        if (found) {
            player.sendMessage("該当箇所：$message")
            Bukkit.getLogger().info("メッセージがフィルターされました. プレイヤー: ${player.name} フィルター内容: $message")
            event.isCancelled = true
        }



    }
}

@Serializable
data class FilterJson(
    val filterMessages: MutableList<String> = mutableListOf()
)
