package com.shilpakala.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shilpakala.data.model.Artwork
import com.shilpakala.data.model.TimelineStage

@Database(entities = [Artwork::class, TimelineStage::class], version = 1, exportSchema = false)
abstract class ShilpaKalaDatabase : RoomDatabase() {

    abstract fun artworkDao(): ArtworkDao
    abstract fun timelineDao(): TimelineDao

    companion object {
        @Volatile
        private var INSTANCE: ShilpaKalaDatabase? = null

        fun getDatabase(context: Context): ShilpaKalaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ShilpaKalaDatabase::class.java,
                    "shilpakala_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
