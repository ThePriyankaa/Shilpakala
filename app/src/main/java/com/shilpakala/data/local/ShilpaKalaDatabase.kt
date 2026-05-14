package com.shilpakala.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shilpakala.data.model.*

@Database(
    entities = [
        Artwork::class,
        TimelineStage::class,
        User::class,
        LikedArtwork::class,
        SavedArtwork::class,
        Inquiry::class,
        UserActivity::class,
        AppNotification::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ShilpaKalaDatabase : RoomDatabase() {

    abstract fun artworkDao(): ArtworkDao
    abstract fun timelineDao(): TimelineDao
    abstract fun userDao(): UserDao
    abstract fun likedArtworkDao(): LikedArtworkDao
    abstract fun savedArtworkDao(): SavedArtworkDao
    abstract fun inquiryDao(): InquiryDao
    abstract fun userActivityDao(): UserActivityDao
    abstract fun notificationDao(): NotificationDao

    companion object {
        @Volatile
        private var INSTANCE: ShilpaKalaDatabase? = null

        fun getDatabase(context: Context): ShilpaKalaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ShilpaKalaDatabase::class.java,
                    "shilpakala_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
