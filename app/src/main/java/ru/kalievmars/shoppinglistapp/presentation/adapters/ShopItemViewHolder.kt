package ru.kalievmars.shoppinglistapp.presentation.adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.kalievmars.shoppinglistapp.R

class ShopItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val nameText: TextView = itemView.findViewById<TextView>(R.id.textView)
    val countText: TextView = itemView.findViewById<TextView>(R.id.textView2)
}