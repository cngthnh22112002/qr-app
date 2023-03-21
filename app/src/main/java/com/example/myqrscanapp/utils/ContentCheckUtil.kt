package com.example.myqrscanapp.utils

import android.util.Patterns
import android.webkit.URLUtil
import java.net.MalformedURLException

object ContentCheckUtil {
    fun isWebUrl(urlString: String?): Boolean {
        return try {
            URLUtil.isValidUrl(urlString) && Patterns.WEB_URL.matcher(urlString.toString()).matches()
        } catch (ignored: MalformedURLException) {
            false
        }
    }
}