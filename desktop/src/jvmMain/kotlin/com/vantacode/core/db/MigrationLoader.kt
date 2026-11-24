package com.vantacode.core.db

import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.SQLiteStatement
import com.vantacode.core.util.FileLogger

class FileMigration(
    val version: Int,
    val scriptName: String,
    val sqlStatements: List<String>
) : Migration(version - 1, version) {

    override fun migrate(db: SQLiteConnection) {
        try {
            db.execSimple(
                "CREATE TABLE IF NOT EXISTS migration_history (" +
                    "version INTEGER PRIMARY KEY," +
                    "script_name TEXT," +
                    "executed_at INTEGER)"
            )
            for (sql in sqlStatements) {
                db.execSimple(sql)
            }
            db.prepare("INSERT OR REPLACE INTO migration_history (version, script_name, executed_at) VALUES (?, ?, ?)").use { stmt ->
                stmt.bindLong(1, version.toLong())
                stmt.bindText(2, scriptName)
                stmt.bindLong(3, System.currentTimeMillis())
                stmt.step()
            }
        } catch (t: Throwable) {
            FileLogger.e("MigrationLoader", "Migration failed: $scriptName", t)
            throw t
        }
        FileLogger.i("MigrationLoader", "Applied migration: $scriptName")
    }
}

/** Execute a statement with no result rows/values; every statement must terminate. */
private fun SQLiteConnection.execSimple(sql: String) {
    prepare(sql).use { stmt ->
        stmt.step()
    }
}

object MigrationLoader {
    fun loadMigrations(): Array<Migration> {
        val assetManager = com.vantacode.core.platform.DesktopAssets
        val migrationsDir = "migrations"
        val files = runCatching { assetManager.list(migrationsDir) }.getOrNull() ?: emptyArray()

        val migrations = mutableListOf<Migration>()

        // File format: {version}_{description}.sql, e.g., "7_add_workspace_path.sql"
        for (fileName in files) {
            if (!fileName.endsWith(".sql")) continue

            val versionStr = fileName.substringBefore('_')
            val version = versionStr.toIntOrNull() ?: continue

            val sqlContent = runCatching {
                assetManager.open("$migrationsDir/$fileName").bufferedReader().use { it.readText() }
            }.getOrNull() ?: continue

            val statements = SqlScriptSplitter.split(sqlContent)
            migrations.add(FileMigration(version, fileName, statements))
        }

        return migrations.toTypedArray()
    }
}
