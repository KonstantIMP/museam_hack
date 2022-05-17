package org.kimp.witelokk.museum.app

import android.app.Application
import android.content.Intent
import com.google.android.material.color.DynamicColors
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import org.kimp.witelokk.museum.app.ui.activities.MainActivity


class MuseumApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        DynamicColors.applyToActivitiesIfAvailable(this)

        val startIntent = Intent(
            this, MainActivity::class.java
        )

        val builder = Picasso.Builder(this)
        builder.downloader(OkHttp3Downloader(baseContext, Long.MAX_VALUE))
        val built = builder.build()
        built.setIndicatorsEnabled(true)
        built.isLoggingEnabled = true
        Picasso.setSingletonInstance(built)

        startIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(startIntent)
    }
}