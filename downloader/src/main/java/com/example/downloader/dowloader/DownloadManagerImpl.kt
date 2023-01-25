package com.example.downloader.dowloader

import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment.DIRECTORY_DOWNLOADS
import androidx.core.content.PermissionChecker
import androidx.core.net.toUri
import com.example.utils.ext.tryOrNull

class DownloadManagerImpl(private val context: Context) : DownloaderManager {

    private val downloadManager = context.getSystemService(DownloadManager::class.java)

    override fun downloadFile(url: String, name: String): Long? {
        val request = DownloadManager.Request(url.toUri())
            .setMimeType("image/jpeg")
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setTitle(name)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(DIRECTORY_DOWNLOADS, name)
        return tryOrNull {
            downloadManager.enqueue(request)
        }
    }
}