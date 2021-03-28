 package com.example.ff.repository

import androidx.lifecycle.LiveData
import com.example.ff.db.FridgeDao
import com.example.ff.model.FridgeItem

class FridgeRepository(private val fridgeDao: FridgeDao) {
    val readAllData: LiveData<List<FridgeItem>> = fridgeDao.readAllData()

    suspend fun addItem(fridgeItem: FridgeItem) {
        fridgeDao.addItem(fridgeItem)
    }

    suspend fun updateItem(fridgeItem: FridgeItem) {
        fridgeDao.updateItem(fridgeItem)
    }

    suspend fun deleteItem(fridgeItem: FridgeItem) {
        fridgeDao.deleteItem(fridgeItem)
    }
    suspend fun deleteAllItems()
    {
        fridgeDao.deleteAllItems()
    }

}