package ru.kalievmars.shoppinglistapp.presentation

import android.app.Application
import ru.kalievmars.shoppinglistapp.di.DaggerShopComponent

class ShopApplication : Application() {

    val component by lazy {
        DaggerShopComponent.factory().create(this)
    }

}