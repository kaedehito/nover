package org.pik6c.nover.utils

import org.bukkit.ChatColor
import java.util.*

class ReplaceMessage {
    companion object{
        fun replaceMessage(value: String, name: String, message: String): String {
            val tempPlayerPlaceholder = "%%PLAYER_${UUID.randomUUID()}%%"

            val replaced = value
                .replace("{player}", tempPlayerPlaceholder)
                .replace("{name}", tempPlayerPlaceholder)
                .replace("{message}", message)
                .replace(tempPlayerPlaceholder, name)
                .replace("{red}", ChatColor.RED.toString())
                .replace("{blue}", ChatColor.BLUE.toString())
                .replace("{green}", ChatColor.GREEN.toString())
                .replace("{aqua}", ChatColor.AQUA.toString())
                .replace("{yellow}", ChatColor.YELLOW.toString())
                .replace("{black}", ChatColor.BLACK.toString())
                .replace("{white}", ChatColor.WHITE.toString())
                .replace("{bold}", ChatColor.BOLD.toString())
                .replace("{italic}", ChatColor.ITALIC.toString())
                .replace("{reset}", ChatColor.RESET.toString())

            return replaced
        }

        fun replaceMessage(value: String, name: String): String{
            val tempPlayerPlaceholder = "%%PLAYER_${UUID.randomUUID()}%%"

            val replaced = value
                .replace("{player}", tempPlayerPlaceholder)
                .replace("{name}", tempPlayerPlaceholder)
                .replace(tempPlayerPlaceholder, name)
                .replace("{red}", ChatColor.RED.toString())
                .replace("{blue}", ChatColor.BLUE.toString())
                .replace("{green}", ChatColor.GREEN.toString())
                .replace("{aqua}", ChatColor.AQUA.toString())
                .replace("{yellow}", ChatColor.YELLOW.toString())
                .replace("{black}", ChatColor.BLACK.toString())
                .replace("{white}", ChatColor.WHITE.toString())
                .replace("{bold}", ChatColor.BOLD.toString())
                .replace("{italic}", ChatColor.ITALIC.toString())
                .replace("{reset}", ChatColor.RESET.toString())

            return replaced
        }


        fun replaceMessage(value: String): String{
            val replaced = value.replace("{red}", ChatColor.RED.toString())
                .replace("{blue}", ChatColor.BLUE.toString())
                .replace("{green}", ChatColor.GREEN.toString())
                .replace("{aqua}", ChatColor.AQUA.toString())
                .replace("{yellow}", ChatColor.YELLOW.toString())
                .replace("{black}", ChatColor.BLACK.toString())
                .replace("{white}", ChatColor.WHITE.toString())
                .replace("{bold}", ChatColor.BOLD.toString())
                .replace("{reset}", ChatColor.RESET.toString())
                .replace("{italic}", ChatColor.ITALIC.toString())

            return replaced
        }
    }
}