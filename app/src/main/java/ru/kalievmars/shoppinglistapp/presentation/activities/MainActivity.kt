package ru.kalievmars.shoppinglistapp.presentation.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import ru.kalievmars.shoppinglistapp.R
import ru.kalievmars.shoppinglistapp.databinding.ActivityMainBinding
import ru.kalievmars.shoppinglistapp.presentation.ShopApplication
import ru.kalievmars.shoppinglistapp.presentation.adapters.ShopRecyclerViewAdapter
import ru.kalievmars.shoppinglistapp.presentation.fragments.ShopItemFragment
import ru.kalievmars.shoppinglistapp.presentation.utils.SwipeToDelete
import ru.kalievmars.shoppinglistapp.presentation.viewmodel.MainViewModel
import ru.kalievmars.shoppinglistapp.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ShopItemFragment.OnFragmentClosedListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory
        )[MainViewModel::class.java]
    }

    private val component by lazy {
        (application as ShopApplication).component
    }

    private val shopAdapter by lazy {
        ShopRecyclerViewAdapter()
    }
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupRecyclerView()

        viewModel.shopList.observe(this) {
            shopAdapter.submitList(it)
        }
        Log.d("MainActivity", "I am here")
        binding.floatingActionButton.setOnClickListener {
            if(isOnePaneMode()) {
                val intent = ShopItemActivity.newIntentAddItem(this)
                startActivity(intent)
            } else {
                val fragment = ShopItemFragment.newInstanceAddItem()
                launchFragment(fragment)
            }
        }
    }

    private fun isOnePaneMode(): Boolean {
        return binding.shopItemContainer == null
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_container, fragment)
            .addToBackStack(null)
            .commit()
    }


    private fun setupRecyclerView() {
        shopAdapter.onShopItemSwipeLeftListener = {
            viewModel.deleteShopItem(it)
        }
        val itemTouchHelper = ItemTouchHelper(SwipeToDelete(shopAdapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

        shopAdapter.onShopItemLongClickListener = {
            viewModel.changeEnableShopItem(it)
        }
        shopAdapter.onShopItemClickListener = {
            if(isOnePaneMode()) {
                val intent = ShopItemActivity.newIntentEditItem(this, it.id)
                startActivity(intent)
            } else {
                val fragment = ShopItemFragment.newInstanceEditItem(it.id)
                launchFragment(fragment)
            }
        }

        with(binding.recyclerView) {
            adapter = shopAdapter

            recycledViewPool.setMaxRecycledViews(
                ShopRecyclerViewAdapter.VIEW_TYPE_DISABLED,
                ShopRecyclerViewAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShopRecyclerViewAdapter.VIEW_TYPE_ENABLED,
                ShopRecyclerViewAdapter.MAX_POOL_SIZE
            )
        }

    }

    override fun onClosed() {
        Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
        Log.d("MainActivity", viewModel.shopList.value.toString())
        Log.d("MainActivity", "hello")
        supportFragmentManager.popBackStack()
    }

}

