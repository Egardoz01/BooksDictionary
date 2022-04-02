package com.booksdictionary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var navController = this.findNavController(R.id.navHostFragment)

        NavigationUI.setupActionBarWithNavController(this, navController)

        this.title = "your title";

        supportActionBar?.setTitle(R.string.booksDictionary);
    }

    override fun onSupportNavigateUp(): Boolean {
        var navController = this.findNavController(R.id.navHostFragment)
        return navController.navigateUp()
    }


}