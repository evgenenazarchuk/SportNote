package com.evgenynaz.sportnote

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.evgenynaz.sportnote.databinding.ActivityLoginBinding

class LoginActivity:AppCompatActivity() {
    lateinit var viewBinding: ActivityLoginBinding

    private val navHostFragment by lazy { (supportFragmentManager.findFragmentById(R.id.txtLogin) as NavHostFragment) }

    private val navController: NavController by lazy { navHostFragment.navController }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)



    }

    /*fun getActiveFragment(): Fragment? {
        return navHostFragment.childFragmentManager.fragments[0]
    }*/

    override fun onBackPressed() {
        if (navController.previousBackStackEntry != null) {
            super.onBackPressed()
        } else {
            finish()
        }
    }
}
