package org.pik6c.nover

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import java.io.File
import java.nio.file.FileAlreadyExistsException
import java.nio.file.Paths
import kotlin.io.path.createDirectory

public class joinMessage : Listener{
    @EventHandler
    public fun onPlayerJoin(e: PlayerJoinEvent){
        val player = e.player


        val file = File("./nover/joinMessages.json")

        if (!file.exists()){
            val path = Paths.get("./nover/")

            try {
                path.createDirectory();
            }catch (e: FileAlreadyExistsException){
                println("Failed to create $path: $e")
            }

            val inputStream = {}.javaClass.classLoader.getResourceAsStream("joinMessages.json")
            val read = inputStream?.bufferedReader().use { it?.readText() }
            read?.let { file.writeText(it) }
        }

        val joinMessages = file.readText()

        val parsed = parseJson(joinMessages)

        if (playerIsModerator(player.name)){
            e.joinMessage = replaceMessage.replaceMessage(parsed.moderatorJoinMessage.random(), player.name)
            return;
        }

        e.joinMessage = replaceMessage.replaceMessage(parsed.messages.random(), player.name)
    }

    public fun playerIsModerator(user: String): Boolean{
        val ops = Json.decodeFromString<List<Ops>>(File("./ops.json").readText())

        ops.forEach {
            println("User: $it == $user: ${it.name == user}");
            return it.name == user
        }

        return false
    }
}


fun parseJson(fileContent: String): JoinMessagesJson{
    val json = Json.decodeFromString<JoinMessagesJson>(fileContent);
    return json;
}


@Serializable
data class JoinMessagesJson(
    val messages: List<String>,
    val moderatorJoinMessage: List<String> = messages,
)




