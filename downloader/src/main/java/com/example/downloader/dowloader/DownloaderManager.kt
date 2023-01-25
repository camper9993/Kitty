package com.example.downloader.dowloader

interface DownloaderManager {
    fun downloadFile(url: String, name: String): Long?
}