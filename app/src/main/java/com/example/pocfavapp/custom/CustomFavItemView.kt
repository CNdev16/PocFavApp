package com.example.pocfavapp.custom

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.pocfavapp.databinding.ViewFavItemBinding

class CustomFavItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: ViewFavItemBinding

    init {
        binding = ViewFavItemBinding.inflate(LayoutInflater.from(context), this, true)
    }
}
