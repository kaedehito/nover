package org.pik6c.nover.listeners

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.pik6c.nover.utils.Ops
import org.pik6c.nover.utils.OpsCache
import org.pik6c.nover.utils.ReplaceMessage
import java.io.File
import java.nio.file.FileAlreadyExistsException
import java.nio.file.FileSystems
import java.nio.file.Paths
import java.nio.file.StandardWatchEventKinds
import kotlin.io.path.createDirectory

class JoinMessage : Listener{
    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent){
        val player = e.player
        val parsed = JoinMessageAsync.getJoinMessage()

        if (OpsCache.isModerator(player.uniqueId.toString())){
            e.joinMessage = ReplaceMessage.replaceMessage(parsed.moderatorJoinMessage.random(), player.name)
            return
        }

        e.joinMessage = ReplaceMessage.replaceMessage(parsed.messages.random(), player.name)
    }
}

object JoinMessageAsync{
    private val quitMessageFile = File("./nover/JoinMessages.json")
    private var joinFormat: JoinMessagesJson = loadJoinMessage()
    private val watchService = FileSystems.getDefault().newWatchService()

    init {
        Thread { watchFileChanges() }.start()
    }

    private fun loadJoinMessage(): JoinMessagesJson {
        return if (quitMessageFile.exists()) {
            val json = Json { ignoreUnknownKeys = true }
            json.decodeFromString<JoinMessagesJson>(quitMessageFile.readText())
        } else {
            val inputStream = {}.javaClass.classLoader.getResourceAsStream("JoinMessages.json")
            val text = inputStream?.bufferedReader()?.readText()
            text?.let { quitMessageFile.writeText(it) }
            val json = Json { ignoreUnknownKeys = true }
            json.decodeFromString<JoinMessagesJson>(quitMessageFile.readText())
        }
    }

    private fun watchFileChanges() {
        val path = quitMessageFile.toPath().parent
        path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY)

        while (true) {
            val key = watchService.take()
            for (event in key.pollEvents()) {
                if (event.context().toString() == quitMessageFile.name) {
                    joinFormat = loadJoinMessage()
                    println("./nover/JoinMessages.json のキャッシュを更新しました")
                }
            }
            key.reset()
        }
    }

    fun getJoinMessage(): JoinMessagesJson{
        return joinFormat
    }

}



fun jsonParse(fileContent: String): JoinMessagesJson {
    val json = Json.decodeFromString<JoinMessagesJson>(fileContent)
    return json
}


@Serializable
data class JoinMessagesJson(
    val messages: List<String>,
    val moderatorJoinMessage: List<String> = messages,
)




