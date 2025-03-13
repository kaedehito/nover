package org.pik6c.nover

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import java.io.File
import java.nio.file.FileAlreadyExistsException
import java.nio.file.Paths
import kotlin.io.path.createDirectory
import kotlin.io.path.exists

public class exitMessage : Listener{
    @EventHandler
    fun onPlayerQuit(e: PlayerQuitEvent){
        val player = e.player;

        val jsonFile = File("./nover/quitMessages.json")
        if (!jsonFile.exists()){
            val inputStream = {}.javaClass.classLoader.getResourceAsStream("quitMessages.json")
            val read = inputStream?.bufferedReader().use { it?.readText() }
            read?.let { jsonFile.writeText(it) }
        }

        val parsed = Json.decodeFromString<quitMessagesJson>(jsonFile.readText());

        if(joinMessage().playerIsModerator(player.name)){
            val message = parsed.moderatorQuitMessage.random()
            e.quitMessage = replaceMessage.replaceMessage(message, player.name)
            return
        }

        val message = parsed.quitMessage.random()
        e.quitMessage = replaceMessage.replaceMessage(message, player.name)
        return

    }
}



@Serializable
data class quitMessagesJson(
    val quitMessage: List<String>,
    val moderatorQuitMessage: List<String> = quitMessage,
)

