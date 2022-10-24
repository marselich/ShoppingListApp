package ru.kalievmars.shoppinglistapp.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.kalievmars.shoppinglistapp.presentation.activities.MainActivity
import ru.kalievmars.shoppinglistapp.presentation.fragments.ShopItemFragment
import ru.kalievmars.shoppinglistapp.presentation.viewmodel.MainViewModel

@ApplicationScope
@Component(modules = [DataModule::class, ShopViewModelModule::class])
interface ShopComponent {

    fun inject(activity: MainActivity)

    fun inject(activity: ShopItemFragment)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ): ShopComponent

    }

}
