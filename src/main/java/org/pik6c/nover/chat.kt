package org.pik6c.nover

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

public class ChatListener : Listener{
    @EventHandler
    public fun onPlayerJoin(e: PlayerJoinEvent){
        val player = e.player;


        val file = File("./nover/joinMessages.json");

        if (!file.exists()){
            val path = Paths.get("./nover/");
            Files.createDirectory(path);
            return;
        }

        val joinMessages = file.readText()

        val parsed = parseJson(joinMessages)

        if (playerIsModerator(player.name)){
            if (parsed.moderatorJoinMessage != null){
                e.joinMessage = parsed.moderatorJoinMessage.random().replace("{}", player.name)
            }
            return;
        }

        e.joinMessage = parsed.messages.random().replace("{}", player.name)
    }
}

fun playerIsModerator(user: String): Boolean{
    val ops = Json.decodeFromString<List<Ops>>(File("./ops.json").readText())

    ops.forEach {
        println("User: $it == $user: ${it.name == user}");
        return it.name == user
    }

    return false
}

fun parseJson(fileContent: String): JoinMessagesJson{
    val json = Json.decodeFromString<JoinMessagesJson>(fileContent);
    return json;
}


@Serializable
data class JoinMessagesJson(
    val messages: List<String>,
    val moderatorJoinMessage: List<String>?,
)




