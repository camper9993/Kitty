package com.example.downloader.dowloader

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.PermissionChecker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DownloadFileWorker(private val context: Context) {

    suspend fun onDownload(fileName : String, desc :String, url : String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            downloadFile(fileName, desc, url)
        } else {
            val hasPermission = hasPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (hasPermission) {
                downloadFile(fileName, desc, url)
            } else {
                withContext(Dispatchers.Main) {
                }
            }
        }

    }

    private suspend fun downloadFile(fileName : String, desc :String, url : String){
        withContext(Dispatchers.IO) {
            try {
                val request = DownloadManager.Request(Uri.parse(url))
                    .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                    .setTitle(fileName)
                    .setDescription(desc)
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setAllowedOverMetered(true)
                    .setAllowedOverRoaming(false)
                    .setDestinationInExternalPublicDir(DIRECTORY_DOWNLOADS, fileName)
                val downloadManager= getSystemService(context, DownloadManager::class.java)
                downloadManager?.enqueue(request)
            } catch (e: Exception) {
                Log.e("Loading", e.stackTraceToString())
            }
        }
    }

    private fun hasPermission(context: Context?, permission: String?): Boolean {
        val permissionState = PermissionChecker.checkSelfPermission(
            context!!,
            permission!!
        )
        return PackageManager.PERMISSION_GRANTED == permissionState
    }
}