package org.pik6c.nover.utils

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File
import java.nio.file.*

object OpsCache {
    private val opsFile = File("./ops.json")
    private var opsSet: Set<String> = loadOps()
    private val watchService = FileSystems.getDefault().newWatchService()

    init {
        Thread { watchFileChanges() }.start()
    }

    private fun loadOps(): Set<String> {
        return if (opsFile.exists()) {
            val json = Json { ignoreUnknownKeys = true }
            json.decodeFromString<List<Ops>>(opsFile.readText()).map { it.uuid }.toSet()
        } else emptySet()
    }

    private fun watchFileChanges() {
        val path = opsFile.toPath().parent
        path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY)

        while (true) {
            val key = watchService.take()
            for (event in key.pollEvents()) {
                if (event.context().toString() == opsFile.name) {
                    opsSet = loadOps()
                    println("ops.json のキャッシュを更新しました")
                }
            }
            key.reset()
        }
    }

    fun isModerator(uuid: String): Boolean = opsSet.contains(uuid)
}

@Serializable
data class Ops(
    val uuid: String,
    val name: String,
    val level: Int,
    val bypassesPlayerLimit: Boolean
)
