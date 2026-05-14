package com.hpcreation.logify_android

/**
 * Centralized runtime configuration for Logify.
 *
 * All defaults are read from the consuming app's BuildConfig fields,
 * which are injected at compile time via Gradle build config flags.
 *
 * Supported Gradle flags (set in your app's build.gradle.kts):
 *
 *   LOGIFY_ENABLED          → Boolean  — master on/off switch
 *   LOGIFY_TIME_FORMAT      → String   — SimpleDateFormat pattern
 *   LOGIFY_JSON_INDENT      → Int      — spaces for JSON pretty-print
 *   LOGIFY_EMOJI_STYLE      → String   — "CLASSIC" | "MINIMAL" | "NONE"
 *
 * All properties remain mutable so runtime overrides are still possible.
 */
object LoggerConfig {

    /**
     * Master switch. Sourced from BuildConfig.LOGIFY_ENABLED.
     * Falls back to false (safe default) if the field is missing.
     */
    @Volatile
    var enabled: Boolean = runCatching {
        val cls = Class.forName(buildConfigClassName())
        cls.getField("LOGIFY_ENABLED").getBoolean(null)
    }.getOrDefault(false)

    /**
     * Timestamp pattern. Sourced from BuildConfig.LOGIFY_TIME_FORMAT.
     * Falls back to a sensible ISO-style default.
     */
    @Volatile
    var timeFormat: String = runCatching {
        val cls = Class.forName(buildConfigClassName())
        cls.getField("LOGIFY_TIME_FORMAT").get(null) as String
    }.getOrDefault("yyyy-MM-dd HH:mm:ss.SSS")

    /**
     * JSON indentation spaces. Sourced from BuildConfig.LOGIFY_JSON_INDENT.
     */
    @Volatile
    var jsonIndent: Int = runCatching {
        val cls = Class.forName(buildConfigClassName())
        cls.getField("LOGIFY_JSON_INDENT").getInt(null)
    }.getOrDefault(4)

    /**
     * Emoji/severity indicator style. Sourced from BuildConfig.LOGIFY_EMOJI_STYLE.
     * Accepts "CLASSIC", "MINIMAL", or "NONE" (case-insensitive).
     */
    @Volatile
    var emojiStyle: EmojiStyle = runCatching {
        val cls = Class.forName(buildConfigClassName())
        val raw = cls.getField("LOGIFY_EMOJI_STYLE").get(null) as String
        EmojiStyle.valueOf(raw.uppercase())
    }.getOrDefault(EmojiStyle.CLASSIC)

    /**
     * Resolves the consuming app's BuildConfig class name at runtime
     * by inspecting the calling stack to find the app's package.
     *
     * Falls back to a common naming pattern when reflection is used.
     */
    private fun buildConfigClassName(): String {
        val stack = Thread.currentThread().stackTrace
        val appFrame = stack.firstOrNull { frame ->
            frame.className.endsWith(".BuildConfig") && !frame.className.startsWith("com.hpcreation.logify")
        }
        return appFrame?.className ?: run {
            // Fallback: walk stack to find app package
            val appClass = stack.firstOrNull { frame ->
                !frame.className.startsWith("com.hpcreation.logify") && !frame.className.startsWith(
                    "java."
                ) && !frame.className.startsWith("dalvik.") && !frame.className.startsWith("android.")
            }
            val pkg = appClass?.className?.substringBeforeLast(".") ?: "com.example.app"
            "$pkg.BuildConfig"
        }
    }
}