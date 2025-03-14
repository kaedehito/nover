package org.pik6c.nover

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import java.io.File

class QuitMessage : Listener{
    @EventHandler
    fun onPlayerQuit(e: PlayerQuitEvent){
        val player = e.player

        val jsonFile = File("./nover/quitMessages.json")
        if (!jsonFile.exists()){
            val inputStream = {}.javaClass.classLoader.getResourceAsStream("quitMessages.json")
            val read = inputStream?.bufferedReader().use { it?.readText() }
            read?.let { jsonFile.writeText(it) }
        }

        val parsed = Json.decodeFromString<QuitMessagesJson>(jsonFile.readText())

        if(JoinMessage().playerIsModerator(player.uniqueId.toString())){
            val message = parsed.moderatorQuitMessage.random()
            e.quitMessage = ReplaceMessage.replaceMessage(message, player.name)
            return
        }

        val message = parsed.quitMessage.random()
        e.quitMessage = ReplaceMessage.replaceMessage(message, player.name)
        return

    }
}



@Serializable
data class QuitMessagesJson(
    val quitMessage: List<String>,
    val moderatorQuitMessage: List<String> = quitMessage,
)

