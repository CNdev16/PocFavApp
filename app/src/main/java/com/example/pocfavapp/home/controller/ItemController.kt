package com.example.pocfavapp.home.controller

import android.util.Log
import android.view.View
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.TypedEpoxyController
import com.example.pocfavapp.CategoryViewItemBindingModel_
import com.example.pocfavapp.R
import com.example.pocfavapp.cardViewItem
import com.example.pocfavapp.home.uimodel.Category
import com.example.pocfavapp.home.uimodel.Item
import com.example.pocfavapp.home.uimodel.ItemUi
import com.google.android.material.card.MaterialCardView

class ItemController : TypedEpoxyController<ItemUi>() {

    var onClickItem: ((Int) -> Unit)? = null
    var onBindCardItem: ((View) -> Unit)? = null

    override fun buildModels(data: ItemUi?) {
        data?.categories?.run(::createCategoriesUi)
        data?.items?.run(::createItemsUi)
    }

    private fun createCategoriesUi(categories: List<Category>) {
        CarouselModel_()
            .id("categories_carousel")
            .padding(Carousel.Padding(12, 8))
            .models(
                categories.map { category ->
                    CategoryViewItemBindingModel_()
                        .id("category_id_${category.id}")
                        .onClickCategory { _ -> }
                }
            ).addTo(this)
    }

    private fun createItemsUi(items: List<Item>) {
        items.forEach { item ->
            cardViewItem {
                id("item_id_${item.id}")
                onClickItem { _ ->
                    this@ItemController.onClickItem?.invoke(item.id)
                }
                onBind { model, view, position ->
                    Log.d("TAG", "createItemsUi: $position")
                    if (position == 1) {
                        val card = view.dataBinding.root.findViewById<MaterialCardView>(R.id.card_view_item)
                        Log.d("TAG", "createItemsUi: $card")
                        this@ItemController.onBindCardItem?.invoke(card)
                    }
                }
            }
        }
    }
}
