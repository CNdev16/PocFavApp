package com.example.pocfavapp.home

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.pocfavapp.R
import com.example.pocfavapp.databinding.FragmentHomeBinding
import com.example.pocfavapp.dialog.Fav
import com.example.pocfavapp.dialog.FavoriteGuidelineDialog
import com.example.pocfavapp.extensions.boundingBox
import com.example.pocfavapp.extensions.getStatusBarHeight
import com.example.pocfavapp.home.controller.ItemController
import com.example.pocfavapp.home.uimodel.ItemUi
import com.example.pocfavapp.utils.BitmapUtils

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private val controller: ItemController by lazy { ItemController() }

    private var cardView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupListener()
        setupItems()

        setFragmentResultListener("key") { _, result ->
            val a = result.getString("key")

            Log.d("TAG", "onViewCreated observeBackstack: $a")
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            setHasFixedSize(true)
            setController(controller)
        }
    }

    private fun setupListener() {
        controller.onBindCardItem = ::onBindCardItem
        controller.onClickItem = ::onClickItem
    }

    private fun setupItems() {
        controller.setData(mockItemUi)
    }

    private fun onBindCardItem(view: View) {
        cardView = view

        Handler().postDelayed({
            binding.recyclerView.doOnLayout {
                cardView?.run {
                    this.post {
                        val statusBarHeight = requireActivity().getStatusBarHeight()

                        val box = this.boundingBox

                        val fav = Fav(
                            top = box.top - statusBarHeight,
                            bottom = box.bottom - statusBarHeight,
                            left = box.left,
                            right = box.right,
                            bmStr = BitmapUtils.encodeBitmapToString(getBitmapFromView(this))
                        )
                        FavoriteGuidelineDialog.newInstance(fav).show(
                            childFragmentManager,
                            FavoriteGuidelineDialog.TAG
                        )
                    }
                }
            }
        }, 250)
    }

    private fun onClickItem(id: Int) {
        findNavController().navigate(R.id.navigate_to_detailFragment)
    }

    private fun getBitmapFromView(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)

        return bitmap
    }

    companion object {
        private val mockItemUi = ItemUi(
            categories = (0..9).map {
                com.example.pocfavapp.home.uimodel.Category(id = it, name = "Item: $it")
            },
            items = (0..9).map {
                com.example.pocfavapp.home.uimodel.Item(id = it, name = "Item: $it")
            }
        )

        @JvmStatic
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}