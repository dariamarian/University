package com.example.myapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myapp.todo.data.Item
import com.example.myapp.todo.data.local.ItemDao

@Database(entities = arrayOf(Item::class), version = 2)
abstract class MyAppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao

    companion object {
        @Volatile
        private var INSTANCE: MyAppDatabase? = null

        private val migration1to2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Create a new table with the desired schema
                database.execSQL("CREATE TABLE IF NOT EXISTS `items_new` " +
                        "(`_id` TEXT NOT NULL, " +
                        "`title` TEXT NOT NULL, " +
                        "`genre` TEXT NOT NULL, " +
                        "`releaseDate` TEXT NOT NULL, " +
                        "`rating` INTEGER NOT NULL, " +
                        "`watched` INTEGER NOT NULL, " +
                        "PRIMARY KEY(`_id`))")

                // Copy the data from the old table to the new table
                database.execSQL("INSERT INTO `items_new` (`_id`, `title`, `genre`, `releaseDate`, `rating`, `watched`) " +
                        "SELECT `_id`, `text` AS `title`, '' AS `genre`, '' AS `releaseDate`, 0 AS `rating`, 0 AS `watched` FROM `items`")

                // Remove the old table
                database.execSQL("DROP TABLE IF EXISTS `items`")

                // Rename the new table to the original table name
                database.execSQL("ALTER TABLE `items_new` RENAME TO `items`")
            }
        }

        fun getDatabase(context: Context): MyAppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    MyAppDatabase::class.java,
                    "app_database"
                )
                    .addMigrations(migration1to2)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
