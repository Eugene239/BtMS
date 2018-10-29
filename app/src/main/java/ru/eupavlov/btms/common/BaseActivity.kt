package ru.eupavlov.btms.common

import android.support.v4.app.Fragment
import com.arellomobile.mvp.MvpAppCompatActivity
import ru.eupavlov.btms.BT_LIST_FRAGMENT
import ru.eupavlov.btms.FILE_FRAGMENT
import ru.eupavlov.btms.R
import ru.eupavlov.btms.TRANSFER_FRAGMENT
import ru.eupavlov.btms.mvp.btlist.BtFragment
import ru.eupavlov.btms.mvp.file.FileFragment
import ru.eupavlov.btms.mvp.transfer.TransferFragment


abstract class BaseActivity : MvpAppCompatActivity() {
    val TAG = javaClass.simpleName

    protected var selectedFragment: Fragment? = null


    fun changeFragment(fragment: BaseFragment) {
        if (fragment == selectedFragment) return
        if (selectedFragment != null) {
            supportFragmentManager.beginTransaction()
                    .remove(selectedFragment!!)
                    .add(R.id.frame, fragment, fragment.TAG)
                    .commitAllowingStateLoss()
        } else {
            supportFragmentManager.beginTransaction()
                    .add(R.id.frame, fragment, fragment.TAG)
                    .commitAllowingStateLoss()
        }
        selectedFragment = fragment
    }

    open fun changeFragment(fragmentId: Int) {
        when (fragmentId) {
            BT_LIST_FRAGMENT -> changeFragment(BtFragment())
            FILE_FRAGMENT -> changeFragment(FileFragment())
            TRANSFER_FRAGMENT-> changeFragment(TransferFragment())
            else -> changeFragment(BaseFragment())
        }
    }

//    open fun showLoading(flag: Boolean) {
//
//    }

    override fun onDestroy() {
        super.onDestroy()
    }
}