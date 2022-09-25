package ru.kalievmars.shoppinglistapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.kalievmars.shoppinglistapp.data.dao.ShopListDao
import ru.kalievmars.shoppinglistapp.domain.models.ShopItem


@Database(entities = [ShopItem::class], version = 1)
abstract class ShopListDatabase : RoomDatabase() {

    abstract fun getShopListDao(): ShopListDao

    companion object {

        private var INSTANCE: ShopListDatabase? = null

        fun getInstance(context: Context): ShopListDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        ShopListDatabase::class.java,
                        "shop_list.db"
                    ).build()
                }
            }


            return INSTANCE!!
        }
    }

}
