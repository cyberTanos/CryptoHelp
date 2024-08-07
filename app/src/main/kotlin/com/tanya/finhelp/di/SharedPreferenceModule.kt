package com.tanya.finhelp.di

import android.content.Context
import com.tanya.finhelp.data.local.SharedPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferenceModule {

    @Provides
    fun provideSharedPref(@ApplicationContext context: Context): SharedPreference {
        val pref = context.getSharedPreferences("shared", Context.MODE_PRIVATE)
        return SharedPreference(pref)
    }
}
