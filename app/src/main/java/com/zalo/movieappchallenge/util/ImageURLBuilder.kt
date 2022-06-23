package com.zalo.movieappchallenge.util

object ImageURLBuilder {
    fun getUrl(path: String?, size: String = "w500"): String {
        if (path == null || path.isEmpty()) return ""
        return "${IMAGE_URL}$size$path"
    }
}