package com.hpcreation.logify_android

/**
 * Controls the visual style of severity indicators in log output.
 *
 * - [CLASSIC]  → emoji icons  (🐛 ℹ️ ⚠️ ❌)
 * - [MINIMAL]  → short labels ([D] [I] [W] [E])
 * - [NONE]     → no prefix at all
 */

enum class EmojiStyle {
    CLASSIC, MINIMAL, NONE
}