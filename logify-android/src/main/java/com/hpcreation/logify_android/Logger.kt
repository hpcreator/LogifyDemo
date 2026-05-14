package com.hpcreation.logify_android

/**
 * Entry-point for Logify initialisation.
 *
 * Call [Logger.init] once — typically in `Application.onCreate()` —
 * before using any logging APIs.
 *
 * Example:
 * ```kotlin
 * // Application.onCreate()
 * LoggerConfig.enabledInRelease = false
 * LoggerConfig.timeFormat       = "yyyy-MM-dd HH:mm:ss.SSS"
 * LoggerConfig.jsonIndent       = 4
 * LoggerConfig.emojiStyle       = EmojiStyle.CLASSIC
 *
 * Logger.init(BuildConfig.DEBUG)
 * ```
 */
/*object Logger {

    *//**
     * Initialises the logging engine.
     *
     * @param isDebug Pass `BuildConfig.DEBUG` so the library knows
     *                whether it is running in a debug build.
     *//*
    fun init(isDebug: Boolean) {
        LogifyCore.setDebug(isDebug)
    }
}*/
