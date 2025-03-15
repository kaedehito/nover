package org.pik6c.nover.listeners

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import org.pik6c.nover.utils.OpsCache
import org.pik6c.nover.utils.ReplaceMessage
import java.io.File
import java.nio.file.FileSystems
import java.nio.file.StandardWatchEventKinds

class QuitMessage : Listener{
    @EventHandler
    fun onPlayerQuit(e: PlayerQuitEvent){
        val player = e.player


        val parsed = QuitMessageAsync.getQuitMessage()
        if(OpsCache.isModerator(player.uniqueId.toString())){
            val message = parsed.moderatorQuitMessage.random()
            e.quitMessage = ReplaceMessage.replaceMessage(message, player.name)
            return
        }

        val message = parsed.quitMessage.random()
        e.quitMessage = ReplaceMessage.replaceMessage(message, player.name)
        return

    }
}


object QuitMessageAsync{
    private val quitMessageFile = File("./nover/QuitMessages.json")
    private var quitFormat: QuitMessagesJson = loadQuitMessage()
    private val watchService = FileSystems.getDefault().newWatchService()

    init {
        Thread { watchFileChanges() }.start()
    }

    private fun loadQuitMessage(): QuitMessagesJson {
        return if (quitMessageFile.exists()) {
            val json = Json { ignoreUnknownKeys = true }
            json.decodeFromString<QuitMessagesJson>(quitMessageFile.readText())
        } else {
            val inputStream = {}.javaClass.classLoader.getResourceAsStream("QuitMessages.json")
            val text = inputStream?.bufferedReader()?.readText()
            text?.let { quitMessageFile.writeText(it) }
            val json = Json { ignoreUnknownKeys = true }
            json.decodeFromString<QuitMessagesJson>(quitMessageFile.readText())
        }
    }

    private fun watchFileChanges() {
        val path = quitMessageFile.toPath().parent
        path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY)

        while (true) {
            val key = watchService.take()
            for (event in key.pollEvents()) {
                if (event.context().toString() == quitMessageFile.name) {
                    quitFormat = loadQuitMessage()
                    println("./nover/QuitMessages.json のキャッシュを更新しました")
                }
            }
            key.reset()
        }
    }

    fun getQuitMessage(): QuitMessagesJson{
        return quitFormat
    }

}


@Serializable
data class QuitMessagesJson(
    val quitMessage: List<String>,
    val moderatorQuitMessage: List<String> = quitMessage,
)

