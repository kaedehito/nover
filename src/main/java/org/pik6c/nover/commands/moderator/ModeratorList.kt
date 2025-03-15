package org.pik6c.nover.commands.moderator

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.pik6c.nover.utils.Ops
import org.pik6c.nover.utils.OpsCache
import java.io.File

class ModeratorList {


    fun listModerator(sender: CommandSender){

        val file = File("./ops.json")
        val json = Json { ignoreUnknownKeys = true }
        val parsed = json.decodeFromString<List<Ops>>(file.readText())

        if(parsed.isEmpty()){
            sender.sendMessage("${ChatColor.YELLOW}モデレーターは存在しません")
            return
        }

        for (p in parsed){
            sender.sendMessage("${ChatColor.YELLOW}${p.name}:  ${ChatColor.AQUA}${p.level}")
        }



    }
}