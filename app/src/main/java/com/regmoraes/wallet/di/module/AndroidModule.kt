package com.regmoraes.wallet.di.module

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.regmoraes.wallet.di.component.ViewComponent
import dagger.Module
import dagger.Provides
import net.danlew.android.joda.JodaTimeAndroid
import javax.inject.Singleton

/**
 *   Copyright {2016} {Rômulo Eduardo G. Moraes}
 **/
@Module
class AndroidModule(private val context: Context){

    @Provides
    @Singleton
    fun providesApplicationContext() : Context {
        return context
    }

    @Provides
    @Singleton
    fun providesSharedPreferences(context: Context) : SharedPreferences {

        return context.getSharedPreferences("wallet", MODE_PRIVATE)
    }
}