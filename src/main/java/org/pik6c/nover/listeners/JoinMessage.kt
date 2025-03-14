package org.pik6c.nover.listeners

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.pik6c.nover.ReplaceMessage
import org.pik6c.nover.moderator.Moderator.NvCommand.Companion.Ops
import java.io.File
import java.nio.file.FileAlreadyExistsException
import java.nio.file.Paths
import kotlin.io.path.createDirectory

class JoinMessage : Listener{
    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent){
        val player = e.player


        val file = File("./nover/joinMessages.json")

        if (!file.exists()){
            val path = Paths.get("./nover/")

            try {
                path.createDirectory()
            }catch (e: FileAlreadyExistsException){
                println("Failed to create $path: $e")
            }

            val inputStream = {}.javaClass.classLoader.getResourceAsStream("joinMessages.json")
            val read = inputStream?.bufferedReader().use { it?.readText() }
            read?.let { file.writeText(it) }
        }

        val joinMessages = file.readText()

        val parsed = jsonParse(joinMessages)

        if (playerIsModerator(player.uniqueId.toString())){
            e.joinMessage = ReplaceMessage.replaceMessage(parsed.moderatorJoinMessage.random(), player.name)
            return
        }

        e.joinMessage = ReplaceMessage.replaceMessage(parsed.messages.random(), player.name)
    }

    fun playerIsModerator(uuid: String): Boolean{
        val ops = Json.decodeFromString<List<Ops>>(File("./ops.json").readText())

        ops.forEach {
            val eq = it.uuid == uuid
            println("User: $it == $uuid: $eq")
            return eq
        }

        return false
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




