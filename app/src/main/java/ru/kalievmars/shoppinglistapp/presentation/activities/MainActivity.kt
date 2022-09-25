package ru.kalievmars.shoppinglistapp.presentation.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.kalievmars.shoppinglistapp.R
import ru.kalievmars.shoppinglistapp.presentation.adapters.ShopRecyclerViewAdapter
import ru.kalievmars.shoppinglistapp.presentation.fragments.ShopItemFragment
import ru.kalievmars.shoppinglistapp.presentation.utils.SwipeToDelete
import ru.kalievmars.shoppinglistapp.presentation.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(), ShopItemFragment.OnFragmentClosedListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var shopAdapter: ShopRecyclerViewAdapter
    private lateinit var floatingButton: FloatingActionButton
    private var shopItemContainer: FragmentContainerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()

        shopItemContainer = findViewById(R.id.shop_item_container)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.shopList.observe(this) {
            Log.d("shopList", it.toString())
            shopAdapter.submitList(it)
        }

        floatingButton = findViewById(R.id.floatingActionButton)

        floatingButton.setOnClickListener {
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
        return shopItemContainer == null
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_container, fragment)
            .addToBackStack(null)
            .commit()
    }


    private fun setupRecyclerView() {
        val rvShopList = findViewById<RecyclerView>(R.id.recycler_view)
        shopAdapter = ShopRecyclerViewAdapter()
        shopAdapter.onShopItemSwipeLeftListener = {
            viewModel.deleteShopItem(it)
        }
        val itemTouchHelper = ItemTouchHelper(SwipeToDelete(shopAdapter))
        itemTouchHelper.attachToRecyclerView(rvShopList)

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

        with(rvShopList) {
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
    }

}

