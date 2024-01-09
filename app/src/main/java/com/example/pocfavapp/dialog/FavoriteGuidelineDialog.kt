package com.example.pocfavapp.dialog

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.doOnLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.pocfavapp.R
import com.example.pocfavapp.databinding.DialogFavoriteGuideLineBinding
import kotlinx.parcelize.Parcelize

@Parcelize
data class Fav(
    val top: Int,
    val bottom: Int,
    val left: Int,
    val right: Int,
    val bm: Bitmap
) : Parcelable

class FavoriteGuidelineDialog : DialogFragment() {
    private lateinit var binding: DialogFavoriteGuideLineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.DialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_favorite_guide_line,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(FAV_ARG, Fav::class.java)
        } else {
            arguments?.getParcelable(FAV_ARG)
        }?.run {
            binding.imageViewItem.apply {
                doOnLayout {
                    top = this@run.top
                    bottom = this@run.bottom
                    left = this@run.left
                    right = this@run.right

                    setImageBitmap(this@run.bm)
                }

                Log.d(
                    "TAG", "setupRecyclerView: doOnLayout => ${this@run}\n" +
                            "rect top: ${this@run.top}\n" +
                            "rect bottom: ${this@run.bottom}\n" +
                            "rect left: ${this@run.left}\n" +
                            "rect right: ${this@run.right}\n"
                )
            }
        }
    }

    companion object {
        private const val FAV_ARG = "fav_args"
        const val TAG = "FavoriteGuidelineDialog"

        fun newInstance(fav: Fav): FavoriteGuidelineDialog {
            return FavoriteGuidelineDialog().apply {
                arguments = bundleOf(
                    FAV_ARG to fav
                )
            }
        }
    }
}
