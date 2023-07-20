package com.qs.tv.tvplayer.splash

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.media3.common.util.UnstableApi
import com.qs.tv.tvplayer.AppNavigator
import com.qs.tv.tvplayer.R
import com.qs.tv.tvplayer.base.BaseActivity
import com.qs.tv.tvplayer.objects.DeviceMemoryObject
import com.qs.tv.tvplayer.utils.PermissionUtil

@UnstableApi
class StartActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        DeviceMemoryObject.isExternalStorageAvailable(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                requestStorageManager.launch(intent)
            } else {
                AppNavigator.navigateToDashboardActivity(this)
            }
        } else {
            if (PermissionUtil.isPermissionGranted(this)) {
                requestPermissions()
            } else {
                AppNavigator.navigateToDashboardActivity(this)
            }
        }
    }

    private fun requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(arrayOf(
                Manifest.permission.READ_MEDIA_VIDEO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_EXTERNAL_STORAGE))
        } else {
            requestPermissionLauncher.launch(arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE))
        }
    }

    private val requestStorageManager: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        requestPermissions()
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                permissions: Map<String, Boolean> ->

            var allowAllPermission = true
            loop@for (entry in permissions.entries) {
                val isGranted = entry.value
                if (!isGranted) {
                    allowAllPermission = false
                    break@loop
                }
            }

            if (allowAllPermission) {
                AppNavigator.navigateToDashboardActivity(this)
            }
        }

    override fun getCurrentFragment(): Fragment? {
        return null
    }
}