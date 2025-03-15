package org.pik6c.nover.commands.moderator

import kotlinx.serialization.json.Json
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.pik6c.nover.utils.Ops
import java.io.File

class ModeratorList {
    fun listModerator(sender: CommandSender){
        val file = File("./ops.json").readText()

        val parsed = Json.decodeFromString<MutableList<Ops>>(file)

        for (p in parsed){
            sender.sendMessage("${ChatColor.YELLOW}${p.name}   ${p.level}")
        }



    }
}