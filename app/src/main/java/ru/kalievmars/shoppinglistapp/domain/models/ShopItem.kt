package ru.kalievmars.shoppinglistapp.domain.models

import android.text.Editable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "shop_list")
data class ShopItem(
    val name: String,
    val count: Int,
    var enabled: Boolean,
    @PrimaryKey
    var id: Int = UNDEFINED_ID
) {

    companion object {
        const val UNDEFINED_ID = -1
    }

//    @Ignore
//    constructor(name: String, count: Int, enabled: Boolean) : this(name, count, enabled, 0)
}