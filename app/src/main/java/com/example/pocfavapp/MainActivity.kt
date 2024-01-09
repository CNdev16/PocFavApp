package com.example.pocfavapp

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.doOnLayout
import androidx.databinding.DataBindingUtil
import com.example.pocfavapp.adapter.Item
import com.example.pocfavapp.adapter.ItemAdapter
import com.example.pocfavapp.dialog.Fav
import com.example.pocfavapp.dialog.FavoriteGuidelineDialog
import com.example.pocfavapp.databinding.ActivityMainBinding
import com.example.pocfavapp.extensions.getStatusBarHeight

val View.screenLocation
    get(): IntArray {
        val point = IntArray(2)
        getLocationInWindow(point)
        return point
    }

val View.boundingBox
    get(): Rect {
        val (x, y) = screenLocation
        return Rect(x, y, x + width, y + height)
    }

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val itemAdapter: ItemAdapter by lazy { ItemAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupRecyclerView()
        setupListener()
        setupItems()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            setHasFixedSize(true)
            adapter = itemAdapter
        }
    }

    private fun setupListener() {
        itemAdapter.onClick = ::onClickItem
    }

    private fun setupItems() {
        itemAdapter.items = mockItems
    }

    private fun onClickItem(id: Int) {
        binding.recyclerView.doOnLayout {
            val viewHolder = binding.recyclerView.findViewHolderForAdapterPosition(0)
            val itemView = viewHolder?.itemView as? ConstraintLayout
            itemView?.let {
                val cardViewItem = it.getViewById(R.id.card_view_item)

                cardViewItem.post {
                    val statusBarHeight = this@MainActivity.getStatusBarHeight()
                    Log.d("TAG", "getStatusBarHeight: $statusBarHeight")

                    val box = cardViewItem.boundingBox
                    Log.d(
                        "TAG", "setupRecyclerView: child => $box\n" +
                                "rect top: ${box.top - statusBarHeight}\n" +
                                "rect bottom: ${box.bottom - statusBarHeight}\n" +
                                "rect left: ${box.left}\n" +
                                "rect right: ${box.right}\n"
                    )

                    val fav = Fav(
                        top = box.top - statusBarHeight,
                        bottom = box.bottom - statusBarHeight,
                        left = box.left,
                        right = box.right,
                        bm = getBitmapFromView(cardViewItem)
                    )
                    FavoriteGuidelineDialog.newInstance(fav).show(
                        supportFragmentManager,
                        FavoriteGuidelineDialog.TAG
                    )
                }
            }
        }
    }

    private fun getBitmapFromView(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)

        return bitmap
    }

    companion object {
        val mockItems = (0..9).map {
            Item(id = it, name = "Item: $it")
        }
    }
}
