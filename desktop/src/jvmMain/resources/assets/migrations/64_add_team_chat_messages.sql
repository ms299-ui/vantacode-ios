-- 团队群聊消息（Teams 功能，配合 agents/*.md 中团队定义文件使用）。
-- 表结构严格对齐 Room schema 64（desktop/schemas/.../64.json），列默认值与 Room 期望不一致可能导致启动校验失败。
CREATE TABLE IF NOT EXISTS `team_chat_messages` (
    `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `teamName` TEXT NOT NULL,
    `speaker` TEXT NOT NULL,
    `avatar` TEXT NOT NULL,
    `role` TEXT NOT NULL,
    `content` TEXT NOT NULL,
    `imagePaths` TEXT NOT NULL,
    `refSessionId` TEXT NOT NULL,
    `createdAt` INTEGER NOT NULL
);
CREATE INDEX IF NOT EXISTS `index_team_chat_messages_teamName` ON `team_chat_messages` (`teamName`);
