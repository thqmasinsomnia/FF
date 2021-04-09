package com.example.ff.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.ff.model.FridgeItem

@Dao
interface FridgeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addItem(fridgeitem: FridgeItem)

    @Update
    suspend fun updateItem(fridgeitem: FridgeItem)

    @Delete
    suspend fun deleteItem(item: FridgeItem)

    @Query("DELETE FROM item_table")
    suspend fun deleteAllItems()

    @Query("SELECT * FROM item_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<FridgeItem>>

}
