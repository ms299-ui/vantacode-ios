package com.vantacode.core.watch

/**
 * 远程目录监听不可用时的异常（桌面端 FileChangeHub 仅支持本地监听）。
 * 保留独立的 code，方便插件侧拿到稳定的错误原因。
 */
class RemoteWatchException(val code: String, message: String) : Exception(message)
