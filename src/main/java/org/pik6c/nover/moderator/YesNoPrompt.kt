package org.pik6c.nover.moderator

import org.bukkit.conversations.ConversationContext
import org.bukkit.conversations.ConversationFactory
import org.bukkit.conversations.Prompt
import org.bukkit.conversations.StringPrompt
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin


class YesNoPrompt(private val plugin: Plugin) {

    fun ask(player: Player, callback: (Boolean) -> Unit) {
        val factory = ConversationFactory(plugin)
            .withFirstPrompt(object : StringPrompt() {
                override fun getPromptText(context: ConversationContext): String {
                    return "§e本当にモデレーター権限を剥奪しますか？（yes/no）"
                }

                override fun acceptInput(context: ConversationContext, input: String?): Prompt? {
                    return when (input?.lowercase()) {
                        "yes", "y", "はい" -> {
                            callback(true)
                            null
                        }
                        "no", "n", "いいえ" -> {
                            callback(false)
                            null
                        }
                        else -> {
                            context.forWhom.sendRawMessage("§c無効な入力です。yes または no を入力してください。")
                            this
                        }
                    }
                }
            })
            .withEscapeSequence("cancel")
            .withTimeout(30)
            .thatExcludesNonPlayersWithMessage("プレイヤーのみが回答できます")

        val conversation = factory.buildConversation(player)
        player.beginConversation(conversation)
    }

}
