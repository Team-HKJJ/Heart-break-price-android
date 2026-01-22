package com.hkjj.heartbreakprice

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import com.hkjj.heartbreakprice.core.di.appModule
import com.hkjj.heartbreakprice.core.di.datasourceModule
import com.hkjj.heartbreakprice.core.di.repositoryModule
import com.hkjj.heartbreakprice.core.di.usecaseModule
import com.hkjj.heartbreakprice.core.di.viewModelModule
import com.hkjj.heartbreakprice.data.service.NotificationMessagingService
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()

        startKoin {
            androidLogger()
            androidContext(this@AppApplication)
            modules(
                appModule,
                datasourceModule,
                repositoryModule,
                usecaseModule,
                viewModelModule
            )
        }
    }

    private fun createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NotificationMessagingService.NOTIFICATION_CHANNEL_ID,
                NotificationMessagingService.NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = NotificationMessagingService.NOTIFICATION_CHANNEL_DESCRIPTION
            }

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }
}