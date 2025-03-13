package org.pik6c.nover

import org.bukkit.ChatColor

class replaceMessage {
    companion object{
        fun replaceMessage(message: String, name: String): String{
            val replaced = message.replace("{red}", ChatColor.RED.toString())
                .replace("{blue}", ChatColor.BLUE.toString())
                .replace("{green}", ChatColor.GREEN.toString())
                .replace("{aqua}", ChatColor.AQUA.toString())
                .replace("{yellow}", ChatColor.YELLOW.toString())
                .replace("{black}", ChatColor.BLACK.toString())
                .replace("{white}", ChatColor.WHITE.toString())
                .replace("{bold}", ChatColor.BOLD.toString())
                .replace("{italic}", ChatColor.ITALIC.toString())
                .replace("{reset}", ChatColor.RESET.toString())
                .replace("{player}", name)
                .replace("{name}", name)
            return replaced
        }

        fun replaceMessage(message: String): String{
            val replaced = message.replace("{red}", ChatColor.RED.toString())
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