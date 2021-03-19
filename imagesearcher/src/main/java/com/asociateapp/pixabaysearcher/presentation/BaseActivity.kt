package com.asociateapp.pixabaysearcher.presentation

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarColor(Color.WHITE)
    }

    protected fun deliverResult(extras: Bundle?) {
        val intent = Intent()
        extras?.let { intent.putExtras(it) }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    protected fun isHomeMenuItem(menuItem: MenuItem): Boolean {
        return menuItem.itemId == android.R.id.home
    }

    private fun setStatusBarColor(color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = color
        }
    }
}
