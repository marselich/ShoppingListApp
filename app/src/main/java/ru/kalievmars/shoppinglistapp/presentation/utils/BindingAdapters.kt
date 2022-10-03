package ru.kalievmars.shoppinglistapp.presentation.utils

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import ru.kalievmars.shoppinglistapp.R

@BindingAdapter("errorInputName")
fun bindErrorInputName(textInputLayout: TextInputLayout, isError: Boolean) {
//    bindError(textInputLayout, isError, R.string.invalid_name)
    val message = if(isError) {
        textInputLayout.context.getString(R.string.invalid_name)
    } else {
        null
    }
    textInputLayout.error = message
}

@BindingAdapter("errorInputCount")
fun bindErrorInputCount(textInputLayout: TextInputLayout, isError: Boolean) {
    bindError(textInputLayout, isError, R.string.invalid_count)
}


private fun bindError(textInputLayout: TextInputLayout, isError: Boolean, resId: Int) {
    val message = if(isError) {
        textInputLayout.context.getString(resId)
    } else {
        null
    }
    textInputLayout.error = message
}