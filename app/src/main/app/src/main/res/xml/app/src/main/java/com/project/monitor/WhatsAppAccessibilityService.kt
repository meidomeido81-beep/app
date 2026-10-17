package com.project.monitor

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.util.Log

class WhatsAppAccessibilityService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (event.packageName == "com.whatsapp") {
            val rootNode: AccessibilityNodeInfo? = rootInActiveWindow
            if (rootNode != null) {
                extractChatContent(rootNode)
                rootNode.recycle()
            }
        }
    }

    private fun extractChatContent(node: AccessibilityNodeInfo) {
        for (i in 0 until node.childCount) {
            val child = node.getChild(i) ?: continue
            if (!child.text.isNullOrEmpty()) {
                val messageText = child.text.toString()
                Log.d("WhatsAppMonitor", "Captured: $messageText")
            }
            extractChatContent(child)
            child.recycle()
        }
    }

    override fun onInterrupt() {}
}
