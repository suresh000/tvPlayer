package com.qs.tv.tvplayer.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

abstract class BaseFragment : Fragment() {

    abstract fun getCurrentFragment(): Fragment?

    protected open fun fragmentManager(): FragmentManager {
        return parentFragmentManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /*protected fun addFragment(addFragment: Fragment) {
        Thread(Runnable {
            var currentFragmentName = ""
            val currentFragment: Fragment? = getCurrentFragment()
            if (currentFragment != null) {
                currentFragmentName = currentFragment.javaClass.name
            }
            if (addFragment.javaClass.name == currentFragmentName) {
                return@Runnable
            }

            val transaction = fragmentManager().beginTransaction()
            transaction.add(R.id.fragmentContainer, addFragment, addFragment::class.java.name)
            transaction.commit()

        }).start()
    }*/

    /*protected fun replaceFragment(addFragment: Fragment, isForceReplace: Boolean = false) {
        Thread(Runnable {
            if (!isForceReplace) {
                var currentFragmentName = ""
                val currentFragment: Fragment? = getCurrentFragment()
                if (currentFragment != null) {
                    currentFragmentName = currentFragment.javaClass.name
                }
                if (addFragment.javaClass.name == currentFragmentName) {
                    return@Runnable
                }
            }

            val transaction = fragmentManager().beginTransaction()
            transaction.replace(R.id.fragmentContainer, addFragment, addFragment::class.java.name)
            transaction.commit()

        }).start()
    }*/

}