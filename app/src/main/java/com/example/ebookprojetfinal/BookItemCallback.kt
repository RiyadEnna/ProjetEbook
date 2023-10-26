package com.example.ebookprojetfinal


interface BookItemCallback {
    fun onCellClick(book:Items)
    fun onSaveBook(book:Items)
}