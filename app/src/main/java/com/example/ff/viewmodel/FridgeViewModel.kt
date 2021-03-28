package com.example.ff.viewmodel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.ff.db.FridgeDB
import com.example.ff.repository.FridgeRepository
import com.example.ff.model.FridgeItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FridgeViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<FridgeItem>>
    private val repository: FridgeRepository

    init {
        val fridgeDao = FridgeDB.getDatabase(application).fridgeDao()
        repository = FridgeRepository(fridgeDao)
        readAllData = repository.readAllData
    }

    fun addItem(fridgeItem: FridgeItem){
        viewModelScope.launch(Dispatchers.IO){
            repository.addItem(fridgeItem)
        }
    }

    fun updateItem(fridgeItem: FridgeItem){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateItem(fridgeItem)
        }
    }

    fun deleteItem(fridgeItem: FridgeItem){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItem(fridgeItem)
        }
    }

    fun deleteAllItems(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllItems()
        }
    }

}