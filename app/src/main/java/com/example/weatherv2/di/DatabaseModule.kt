package com.example.weatherv2.di

import android.content.Context
import com.example.weatherv2.Database
import com.squareup.sqldelight.android.AndroidSqliteDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideSqliteDriver(@ApplicationContext appContext: Context): AndroidSqliteDriver = AndroidSqliteDriver(
        schema = Database.Schema,
        context = appContext,
        name = "TownItems.db"
    )

    @Singleton
    @Provides
    fun provideQueries(androidSqlDriver: AndroidSqliteDriver) = Database(androidSqlDriver).townItemsQueries

}