package com.qs.tv.tvplayer.dashboard

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.qs.tv.tvplayer.R
import com.qs.tv.tvplayer.base.BaseActivity
import com.qs.tv.tvplayer.databinding.ActivityDashboardBinding


class DashboardActivity : BaseActivity() {

    private lateinit var mBinding: ActivityDashboardBinding
    private lateinit var mVm: DashboardViewModel
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)

        val repository = DashboardRepository(this)
        val factory = DashboardViewModelFactory(repository)
        mVm = ViewModelProvider(this, factory)[DashboardViewModel::class.java]
        mBinding.vm = mVm
        mBinding.repository = mVm.mRepository

        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        navController.addOnDestinationChangedListener(navDestinationListener)

        lifecycle.addObserver(DashboardLifecycleObserver(applicationContext, mVm))

        /*val externalStorageVolumes: Array<out File> =
            ContextCompat.getExternalFilesDirs(applicationContext, null)
        val primaryExternalStorage: File = externalStorageVolumes[0]*/

    }

    private val navDestinationListener =
        NavController.OnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.homeFragment -> {

                }

                else -> {

                }
            }
        }

    override fun getCurrentFragment(): Fragment? {
        return null
    }
}