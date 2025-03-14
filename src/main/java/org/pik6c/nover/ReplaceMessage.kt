package org.pik6c.nover

import org.bukkit.ChatColor

class ReplaceMessage {
    companion object{
        fun replaceMessage(value: String, name: String): String{
            val replaced = value.replace("{red}", ChatColor.RED.toString())
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

        fun replaceMessage(value: String, name: String, message: String): String{
            val replaced = value.replace("{red}", ChatColor.RED.toString())
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
                .replace("{message}", message)
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