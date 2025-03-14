package org.pik6c.nover.moderator

import kotlinx.serialization.Serializable

class Moderator {

    abstract class NvCommand {
        companion object {
            @Serializable
            data class Ops(
                val uuid: String,
                val name: String,
                val level: Int,
                val bypassesPlayerLimit: Boolean
            )
        }



    }



}