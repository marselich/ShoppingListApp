package ru.kalievmars.shoppinglistapp.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import ru.kalievmars.shoppinglistapp.data.dao.ShopListDao


@Database(entities = [ShopItemDbModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getShopListDao(): ShopListDao

    @InternalCoroutinesApi
    companion object {

        private var INSTANCE: AppDatabase? = null
        private val lock = Any()
        private const val DATABASE_NAME = "shop_list.db"

        fun getInstance(application: Application): AppDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(lock) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    application,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = db
                return db
            }
        }
    }

}
