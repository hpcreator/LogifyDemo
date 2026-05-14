package com.hpcreation.logify_android

import com.hpcreation.logify_android.LogFormatter.toJsonOrString
import com.hpcreation.logify_android.LogifyCore.logFormatted

/** Default Logcat tag used when the caller does not supply one. */
const val DEFAULT_TAG = "Logify"


// ── DEBUG ────────────────────────────────────────────────────

/** Log a debug message. */
fun logDebug(message: String, tag: String = DEFAULT_TAG) =
    LogifyCore.log(LogLevel.DEBUG, message, tag)

/** Log a serializable object at DEBUG level (pretty JSON when possible). */
inline fun <reified T> logDebug(value: T, tag: String = DEFAULT_TAG) {
    val (body, isJson) = toJsonOrString(value)
    logFormatted(LogLevel.DEBUG, body, tag, isJson)
}

// ── INFO ─────────────────────────────────────────────────────

/** Log an informational message. */
fun logInfo(message: String, tag: String = DEFAULT_TAG) =
    LogifyCore.log(LogLevel.INFO, message, tag)

/** Log a serializable object at INFO level (pretty JSON when possible). */
inline fun <reified T> logInfo(value: T, tag: String = DEFAULT_TAG) {
    if (!LoggerConfig.enabled) return
    val (body, isJson) = toJsonOrString(value)
    logFormatted(LogLevel.INFO, body, tag, isJson)  // ✅
}

// ── WARN ─────────────────────────────────────────────────────

/** Log a warning message. */
fun logWarn(message: String, tag: String = DEFAULT_TAG) =
    LogifyCore.log(LogLevel.WARN, message, tag)

/** Log a serializable object at WARN level (pretty JSON when possible). */
inline fun <reified T> logWarn(value: T, tag: String = DEFAULT_TAG) {
    val (body, isJson) = toJsonOrString(value)
    logFormatted(LogLevel.WARN, body, tag, isJson)
}

// ── ERROR ────────────────────────────────────────────────────

/** Log an error message. */
fun logError(message: String, tag: String = DEFAULT_TAG) =
    LogifyCore.log(LogLevel.ERROR, message, tag)

/** Log an error message with an associated [Throwable]. */
fun logError(message: String, throwable: Throwable, tag: String = DEFAULT_TAG) =
    LogifyCore.log(LogLevel.ERROR, message, tag, throwable)

/** Log a serializable object at ERROR level (pretty JSON when possible). */
inline fun <reified T> logError(value: T, tag: String = DEFAULT_TAG) {
    val (body, isJson) = toJsonOrString(value)
    logFormatted(LogLevel.ERROR, body, tag, isJson)
}
