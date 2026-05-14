package com.hpcreation.logify_android

import android.util.Log

/**
 * Internal logging engine.
 *
 * No initialization needed — reads state from [LoggerConfig] which is
 * itself populated from compile-time BuildConfig fields.
 */
object LogifyCore {

    internal fun log(
        level    : LogLevel,
        message  : String,
        tag      : String,
        throwable: Throwable? = null,
        multiLine: Boolean = false
    ) {
        if (!LoggerConfig.enabled) return

        val metadata  = extractCallerMetadata()
        val formatted = LogFormatter.format(level, message, metadata, multiLine)

        when (level) {
            LogLevel.DEBUG -> if (throwable != null) Log.d(tag, formatted, throwable) else Log.d(tag, formatted)
            LogLevel.INFO  -> if (throwable != null) Log.i(tag, formatted, throwable) else Log.i(tag, formatted)
            LogLevel.WARN  -> if (throwable != null) Log.w(tag, formatted, throwable) else Log.w(tag, formatted)
            LogLevel.ERROR -> if (throwable != null) Log.e(tag, formatted, throwable) else Log.e(tag, formatted)
        }
    }

    @PublishedApi
    internal fun logFormatted(
        level    : LogLevel,
        body     : String,
        tag      : String,
        multiLine: Boolean
    ) = log(level, body, tag, multiLine = multiLine)
}