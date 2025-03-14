package org.pik6c.nover.listeners

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.pik6c.nover.utils.ReplaceMessage
import java.nio.file.FileAlreadyExistsException
import java.nio.file.Files
import java.nio.file.Path


class ChatColor : Listener{

    private val parsed: ChatFormat by lazy {
        val jsonFile = Path.of("./nover/chatFormat.json")
        val dir = jsonFile.parent


        try {
            Files.createDirectory(dir)
        }catch (e: FileAlreadyExistsException){
            println("Failed to create nover directory: $e")
        }

        val file = jsonFile.toFile()

        if (!file.exists()){
            {}.javaClass.classLoader.getResourceAsStream("chatFormat.json")?.use { input ->
                file.writeText(input.bufferedReader().readText())
            }
        }

        val parsed = Json.decodeFromString<ChatFormat>(file.readText())

        Bukkit.getLogger().info("フォーマットを読み込みました: $parsed")
        parsed
    }


    @EventHandler
    fun onPlayerChat(event: AsyncPlayerChatEvent){
        val player = event.player
        val message = event.message

        if (JoinMessage().playerIsModerator(player.uniqueId.toString())){
            event.format = ReplaceMessage.replaceMessage(parsed.moderatorChat, player.name, message)
            return
        }

        event.format = ReplaceMessage.replaceMessage(parsed.defaultChat, player.name, message)
        return
    }

}

@Serializable
data class ChatFormat(
    val defaultChat: String,
    val moderatorChat: String = defaultChat,
)

