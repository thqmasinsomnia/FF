package com.example.ff.model

import android.os.Parcelable
import java.util.*
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.ParcelField
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
@Entity(tableName = "item_table")
data class FridgeItem(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val name: String,
        val expDate: String
): Parcelable