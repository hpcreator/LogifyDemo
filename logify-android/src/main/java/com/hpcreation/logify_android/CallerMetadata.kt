package com.hpcreation.logify_android

/**
 * Holds contextual information about the call-site that triggered a log.
 *
 * @property fileName   Name of the source file (e.g. "HomeViewModel.kt")
 * @property methodName Name of the enclosing method  (e.g. "loadData")
 * @property lineNumber Line number within that file   (e.g. 42)
 */
data class CallerMetadata(
    val fileName  : String,
    val methodName: String,
    val lineNumber: Int
) {
    /** Formatted as `[HomeViewModel.kt] (loadData:42)` */
    override fun toString(): String = "[$fileName] ($methodName:$lineNumber)"
}

/**
 * Walks the current thread's stack trace and returns the first frame
 * that does NOT belong to the Logify library itself.
 *
 * Returns a placeholder when the call-site cannot be determined.
 */
internal fun extractCallerMetadata(): CallerMetadata {
    val logifyPackage = "com.hpcreation.logify"
    val frames = Thread.currentThread().stackTrace

    val callerFrame = frames.firstOrNull { frame ->
        !frame.className.startsWith(logifyPackage) &&
                frame.className != "dalvik.system.VMStack" &&
                frame.className != "java.lang.Thread"
    }

    return if (callerFrame != null) {
        CallerMetadata(
            fileName   = callerFrame.fileName   ?: "Unknown.kt",
            methodName = callerFrame.methodName ?: "unknown",
            lineNumber = callerFrame.lineNumber
        )
    } else {
        CallerMetadata("Unknown.kt", "unknown", -1)
    }
}