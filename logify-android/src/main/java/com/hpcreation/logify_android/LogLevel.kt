package com.hpcreation.logify_android

/**
 * Severity levels supported by Logify.
 * Each level carries its indicator strings for every [EmojiStyle].
 */
enum class LogLevel(
    val classic: String, val minimal: String
) {
    DEBUG(classic = "🐛", minimal = "[D]"), INFO(
        classic = "ℹ️",
        minimal = "[I]"
    ),
    WARN(classic = "⚠️", minimal = "[W]"), ERROR(classic = "❌", minimal = "[E]");

    /** Returns the appropriate indicator given the current [EmojiStyle]. */
    fun indicator(style: EmojiStyle): String = when (style) {
        EmojiStyle.CLASSIC -> classic
        EmojiStyle.MINIMAL -> minimal
        EmojiStyle.NONE -> ""
    }
}