package com.example.famfo

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    val permissions = arrayOf(
        android.Manifest.permission.READ_CONTACTS
    )
    val permissionCode = 78

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val bottomBar = findViewById<BottomNavigationView>(R.id.bottonNavigation)

        if (isAllPermissionsGranted()) {
            allPermissionGranted()
        } else {
            askForPermission()
        }

        // Instead of if else, kotlin mai when hai
        bottomBar.setOnItemSelectedListener {menuItem ->

            when (menuItem.itemId) {
                R.id.nav_home -> {
                    inflateFragment(HomeFragment.newInstance())
                }
                R.id.nav_dashboard -> {
                    inflateFragment(DashboardFragment.newInstance())
                }
                R.id.nav_guard -> {
                    inflateFragment(GuardFragment.newInstance())
                }
                R.id.nav_profile -> {
                    inflateFragment(ProfileFragment.newInstance())
                }
            }

            true
        }
        bottomBar.selectedItemId = R.id.nav_home
    }

    private fun inflateFragment(newInstance: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, newInstance)
        transaction.commit()
    }

    private fun isAllPermissionsGranted(): Boolean {
        for (item in permissions) {
            if (ContextCompat
                    .checkSelfPermission(
                        this,
                        item
                    ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    private fun allPermissionGranted(): Boolean {
        for (item in permissions) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    item
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }

        return true
    }

    private fun askForPermission() {
        ActivityCompat.requestPermissions(this, permissions, permissionCode)
    }

}