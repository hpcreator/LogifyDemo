package com.hpcreation.logify_android

import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Responsible for assembling the final log string from its constituent parts.
 *
 * Output format:
 * ```
 * 🐛 2025-01-15 14:22:10.123 [UserRepo.kt] (fetchUser:42) → Fetching user
 * ```
 *
 * For serializable objects the body is replaced with pretty-printed JSON:
 * ```
 * ℹ️ 2025-01-15 14:22:10.123 [UserRepo.kt] (fetchUser:42) →
 * {
 *     "id": 1,
 *     "name": "Harsh"
 * }
 * ```
 */
object LogFormatter {

    /** Thread-local SDF so we avoid synchronization overhead. */
    private val sdfThreadLocal = ThreadLocal.withInitial {
        SimpleDateFormat(LoggerConfig.timeFormat, Locale.getDefault())
    }

    /** Returns the current timestamp respecting [LoggerConfig.timeFormat]. */
    private fun timestamp(): String {
        // Re-create SDF only when the pattern has changed.
        val sdf = sdfThreadLocal.get()!!
        if (sdf.toPattern() != LoggerConfig.timeFormat) {
            sdfThreadLocal.set(SimpleDateFormat(LoggerConfig.timeFormat, Locale.getDefault()))
        }
        return sdfThreadLocal.get()!!.format(Date())
    }

    /**
     * Formats a plain (non-serializable) log message.
     *
     * @param level    Severity level
     * @param message  Already-stringified body (maybe multi-line for JSON)
     * @param metadata Call-site context
     * @param multiLine When `true` the arrow sits on its own line before the body
     */
    fun format(
        level: LogLevel, message: String, metadata: CallerMetadata, multiLine: Boolean = false
    ): String {
        val indicator = level.indicator(LoggerConfig.emojiStyle)
        val prefix = buildString {
            if (indicator.isNotEmpty()) append("$indicator ")
            append("${timestamp()} $metadata →")
        }
        return if (multiLine) "$prefix\n$message" else "$prefix $message"
    }

    /**
     * Attempts to serialise [value] to indented JSON using kotlinx.serialization.
     * Falls back to [Any.toString] on failure.
     */
    @PublishedApi
    internal inline fun <reified T> toJsonOrString(value: T): Pair<String, Boolean> {
        return try {
            val json = Json {
                prettyPrint = true
                prettyPrintIndent = " ".repeat(LoggerConfig.jsonIndent)
                encodeDefaults = true
            }
            Pair(json.encodeToString(value), true)
        } catch (_: SerializationException) {
            Pair(value.toString(), false)
        } catch (_: Exception) {
            Pair(value.toString(), false)
        }
    }
}