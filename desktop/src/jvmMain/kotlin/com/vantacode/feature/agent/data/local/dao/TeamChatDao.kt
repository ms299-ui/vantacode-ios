/*
 * VantaCode Desktop — 团队群聊消息 DAO。
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.feature.agent.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.vantacode.feature.agent.data.local.entity.TeamChatMessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamChatDao {
    /** 按时间正序整表观察（Room invalidation tracker 驱动重组）。 */
    @Query("SELECT * FROM team_chat_messages WHERE teamName = :teamName ORDER BY id ASC")
    fun observeMessages(teamName: String): Flow<List<TeamChatMessageEntity>>

    @Query("SELECT * FROM team_chat_messages WHERE teamName = :teamName ORDER BY id ASC")
    suspend fun getMessagesOnce(teamName: String): List<TeamChatMessageEntity>

    @Insert
    suspend fun insert(message: TeamChatMessageEntity): Long

    /** 清空某个团队的全部群聊记录。 */
    @Query("DELETE FROM team_chat_messages WHERE teamName = :teamName")
    suspend fun clear(teamName: String)

    /** 团队改名后把聊天记录一起迁过去。 */
    @Query("UPDATE team_chat_messages SET teamName = :newName WHERE teamName = :oldName")
    suspend fun renameTeam(oldName: String, newName: String)

    /** 只保留最新的 [keep] 条。 */
    @Query(
        """DELETE FROM team_chat_messages
           WHERE teamName = :teamName
           AND id NOT IN (SELECT id FROM team_chat_messages WHERE teamName = :teamName ORDER BY id DESC LIMIT :keep)"""
    )
    suspend fun trim(teamName: String, keep: Int)

    @Query("SELECT COUNT(*) FROM team_chat_messages WHERE teamName = :teamName")
    suspend fun count(teamName: String): Int
}
