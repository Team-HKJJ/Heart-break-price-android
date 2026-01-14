package com.hkjj.heartbreakprice

import android.app.Application
import com.hkjj.heartbreakprice.core.di.datasourceModule
import com.hkjj.heartbreakprice.core.di.repositoryModule
import com.hkjj.heartbreakprice.core.di.usecaseModule
import com.hkjj.heartbreakprice.core.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@AppApplication)
            modules(datasourceModule, repositoryModule, usecaseModule, viewModelModule)
        }
    }
}