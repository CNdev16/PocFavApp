package com.example.pocfavapp.dialog

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.Constraints
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.os.bundleOf
import androidx.core.view.doOnLayout
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.pocfavapp.R
import com.example.pocfavapp.databinding.DialogFavoriteGuideLineBinding
import com.example.pocfavapp.utils.BitmapUtils
import kotlinx.parcelize.Parcelize

@Parcelize
data class Fav(
    val top: Int,
    val bottom: Int,
    val left: Int,
    val right: Int,
    val bmStr: String
) : Parcelable

class FavoriteGuidelineDialog : DialogFragment() {
    private lateinit var binding: DialogFavoriteGuideLineBinding

    private var favModel: Fav? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NO_TITLE, R.style.DialogTheme)

        favModel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(ARG_FAV, Fav::class.java)
        } else {
            arguments?.getParcelable(ARG_FAV)
        }
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

        favModel?.run(::setupView)
    }

    private fun setupView(fav: Fav) {
        binding.content.apply {
            binding.imageViewItem.setImageBitmap(BitmapUtils.decodeStringToBitmap(fav.bmStr))
            animate()
                .translationY(fav.top.toFloat() - (fav.bottom.toFloat() / 2))
                .setDuration(0)
                .start()
        }
    }

    companion object {
        private const val ARG_FAV = "fav"
        const val TAG = "FavoriteGuidelineDialog"

        @JvmStatic
        fun newInstance(fav: Fav): FavoriteGuidelineDialog {
            return FavoriteGuidelineDialog().apply {
                arguments = bundleOf(
                    ARG_FAV to fav
                )
            }
        }
    }
}
