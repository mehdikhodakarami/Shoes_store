package com.example.nikestore_tir_1403.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nikestore_tir_1403.data.Product
import com.example.nikestore_tir_1403.data.repo.source.ProductLocalDataSource

@Database(entities = [Product ::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getProductDao():ProductLocalDataSource
}