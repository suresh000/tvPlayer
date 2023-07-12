package com.qs.tv.tvplayer.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.qs.tv.tvplayer.common.internet.InternetLostDialog

abstract class BaseActivity : AppCompatActivity() {

    abstract fun getCurrentFragment(): Fragment?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        InternetLostDialog.prepareDialog(this)
    }

    /*protected fun addFragment(addFragment: Fragment) {
        Thread(Runnable {
            var currentFragmentName = ""
            val currentFragment = currentFragment
            if (currentFragment != null) {
                currentFragmentName = currentFragment.javaClass.name
            }
            if (addFragment.javaClass.name == currentFragmentName) {
                return@Runnable
            }
            val fragmentManager = supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.add(R.id.fragmentContainer, addFragment)
            transaction.commit()
        }).start()
    }*/

    /*protected fun replaceFragment(addFragment: Fragment) {
        Thread(Runnable {
            var currentFragmentName = ""
            val currentFragment = currentFragment
            if (currentFragment != null) {
                currentFragmentName = currentFragment.javaClass.name
            }
            if (addFragment.javaClass.name == currentFragmentName) {
                return@Runnable
            }
            val fragmentManager = supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, addFragment)
            transaction.commit()
        }).start()
    }*/

}