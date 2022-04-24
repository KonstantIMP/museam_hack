package org.kimp.witelokk.museum.app

import android.app.Application
import android.content.Intent
import com.google.android.material.color.DynamicColors
import org.kimp.witelokk.museum.app.ui.activities.MainActivity

class MuseumApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        DynamicColors.applyToActivitiesIfAvailable(this)

        val startIntent = Intent(
            this, MainActivity::class.java
        )

        startIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(startIntent)
    }
}