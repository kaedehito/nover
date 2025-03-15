package org.pik6c.nover.listeners

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.pik6c.nover.utils.Ops
import org.pik6c.nover.utils.OpsCache
import org.pik6c.nover.utils.ReplaceMessage
import java.io.File
import java.nio.file.*


class ChatColor : Listener {

    @EventHandler
    fun onPlayerChat(event: AsyncPlayerChatEvent) {
        val player = event.player
        val message = event.message
        val chatFormat = ChatFormatJson.getFormat()

        val format = if (OpsCache.isModerator(player.uniqueId.toString())) {
            chatFormat.moderatorChat
        } else {
            chatFormat.defaultChat
        }

        event.format = ReplaceMessage.replaceMessage(format, player.name, message)
    }

}

object ChatFormatJson{
    private val formatFile = File("./nover/ChatFormat.json")
    private var format: ChatFormat = loadFormat()
    private val watchService = FileSystems.getDefault().newWatchService()

    init {
        Thread { watchFileChanges() }.start()
    }

    private fun loadFormat(): ChatFormat {
        return if (formatFile.exists()) {
            val json = Json { ignoreUnknownKeys = true }
            json.decodeFromString<ChatFormat>(formatFile.readText())
        } else {
            val inputStream = {}.javaClass.classLoader.getResourceAsStream("ChatFormat.json")
            val text = inputStream?.bufferedReader()?.readText()
            text?.let { formatFile.writeText(it) }
            val json = Json { ignoreUnknownKeys = true }
            json.decodeFromString<ChatFormat>(formatFile.readText())
        }
    }

    private fun watchFileChanges() {
        val path = formatFile.toPath().parent
        path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY)

        while (true) {
            val key = watchService.take()
            for (event in key.pollEvents()) {
                if (event.context().toString() == formatFile.name) {
                    format = loadFormat()
                    println("./nover/ChatFormat.json のキャッシュを更新しました")
                }
            }
            key.reset()
        }
    }

    fun getFormat(): ChatFormat{
        return format
    }

}

@Serializable
data class ChatFormat(
    val defaultChat: String,
    val moderatorChat: String = defaultChat,
)

