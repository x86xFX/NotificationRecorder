package me.theek.nr.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.theek.nr.data.local.PostNotificationDatabase
import me.theek.nr.data.local.dao.NotificationDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): PostNotificationDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = PostNotificationDatabase::class.java,
            name = "nr.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideNotificationDao(postNotificationDatabase: PostNotificationDatabase): NotificationDao {
        return postNotificationDatabase.notificationDao()
    }
}