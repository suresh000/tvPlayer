package com.qs.tv.tvplayer

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.qs.tv.tvplayer.receiver.NetworkChangeReceiver
import com.qs.tv.tvplayer.utils.helpers.ExceptionHelper
import com.qs.tv.tvplayer.utils.helpers.LogHelper

class AppController : MultiDexApplication() {

    companion object {
        @get:Synchronized
        lateinit var instance: AppController private set
    }

    private lateinit var mNetworkReceiver: NetworkChangeReceiver

    override fun onCreate() {
        super.onCreate()
        instance = this

        registerNetworkReceiver()

    }

    private fun registerNetworkReceiver() {
        mNetworkReceiver = NetworkChangeReceiver()
        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)

        registerReceiver(mNetworkReceiver, intentFilter)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
        LogHelper.setLog(!BuildConfig.IS_PROD || BuildConfig.DEBUG)
        ExceptionHelper.setException(!BuildConfig.IS_PROD || BuildConfig.DEBUG)
    }
}