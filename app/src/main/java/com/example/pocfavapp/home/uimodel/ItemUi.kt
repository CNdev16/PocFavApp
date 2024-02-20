package com.example.pocfavapp.home.uimodel

data class ItemUi(val categories: List<Category>, val items: List<Item>)
data class Item(val id: Int, val name: String)
data class Category(val id: Int, val name: String)
